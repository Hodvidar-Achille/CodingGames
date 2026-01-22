package com.hodvidar.miscellaneous.filewriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFileUpdater {

    // Constantes à configurer selon vos besoins
    static final String VERSION = "J4_01";
    static final String TRIGRAMME = "AGE";
    static final String DATE = "13.01.2026";
    static final String MESSAGE = "Fix nombre instances String";
    private static final String RESOURCES_TEST = "src" + File.separator + "test" + File.separator + "resources";
    // Patterns pour les expressions régulières
    private static final Pattern CARTOCHE_END_PATTERN =
            Pattern.compile("^\\s*\\*{2}\\s*\\+\\+MAJ.*");
    // Sortir la version depuis le cartouche (ex: D1.008)
    private static final Pattern VERSION_PATTERN =
            Pattern.compile("(D\\d+\\.\\d{3})|(B\\d+\\.\\d{3})|(D\\d+\\.\\d{2})|(B\\d+\\.\\d{2})|(D\\d+\\.\\d{1,})|(B\\d+\\.\\d{1,})");
    // Pour modif des methodes comme : onVersion(IDefaultEvent event) {return "D1.000";}
    private static final Pattern ON_VERSION_PATTERN =
            Pattern.compile("(onVersion\\s*\\([^)]*\\)\\s*\\{[^}]*return\\s*\")([^\"]*)(\"[^}]*\\})", Pattern.DOTALL);
    // Pour modif des variables comme : static final String VERSION = "D1.000";
    private static final Pattern STATIC_VERSION_PATTERN =
            Pattern.compile("(\\s*(?:private|protected|public)?\\s*(?:static\\s+)?(?:final\\s+)?String\\s+VERSION\\s*=\\s*\")([^\"]*)(\"\\s*;)");
    private static final String LINE_BREAK = "\n";
    private static final String CARTOUCHE_START_MARKER_1 = "/* =============================================================================";
    private static final String CARTOUCHE_END_MARKER_1 = "============================================================================= */";
    private static final String CARTOUCHE_MARKER_2 = "// =============================================================================";
    private static String new_sub_version = null;
    private static boolean isUsingCartouche1 = true;

    public static void main(final String[] args) {
        final String inputFileName = RESOURCES_TEST + File.separator + "filewriter" + File.separator + "fichier_liste.txt";

        // Essayer de charger le fichier depuis les ressources
        final InputStream resourceStream = JavaFileUpdater.class.getClassLoader()
                .getResourceAsStream(inputFileName);

        List<String> filePaths = new ArrayList<>();

        if (resourceStream != null) {
            // Lire depuis les ressources
            try (final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    filePaths.add(line.trim());
                }
            } catch (final IOException e) {
                System.err.println("Erreur de lecture du fichier ressource: " + e.getMessage());
                return;
            }
        } else {
            // Essayer de lire depuis le système de fichiers
            try {
                filePaths = Files.readAllLines(Paths.get(inputFileName), StandardCharsets.UTF_8);
            } catch (final IOException e) {
                System.err.println("Erreur: fichier '" + inputFileName + "' non trouvé ni dans les ressources ni dans le répertoire courant.");
                System.out.println("Placez le fichier dans:");
                System.out.println("- Le classpath (src/main/resources)");
                System.out.println("- Ou le répertoire courant");
                return;
            }
        }

        System.out.println("Nombre de fichiers à traiter: " + filePaths.size());

        int processed = 0;
        int errors = 0;

        for (final String filePath : filePaths) {
            try {
                if (updateJavaFile(filePath.trim())) {
                    processed++;
                    System.out.println("✓ Traité: " + filePath);
                }
            } catch (final Exception e) {
                errors++;
                System.err.println("✗ Erreur avec " + filePath + ": " + e.getMessage());
            }
        }

        System.out.println("\nRésumé:");
        System.out.println("Fichiers traités avec succès: " + processed);
        System.out.println("Erreurs: " + errors);
    }

    private static boolean updateJavaFile(final String filePath) throws IOException {
        new_sub_version = "D1.000";
        final Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.err.println("Fichier non trouvé: " + filePath);
            return false;
        }

        // Lire le contenu du fichier
        String content = Files.readString(path);
        final String originalContent = content;

        // 1. Trouver et mettre à jour le cartouche
        content = updateCartouche(content);

        // 2. Mettre à jour la méthode onVersion si présente
        content = updateOnVersionMethod(content);

        // 3. Mettre à jour la variable VERSION si présente
        content = updateStaticVersion(content);

        // Écrire le fichier si des modifications ont été faites
        if (!content.equals(originalContent)) {
            Files.writeString(path, content);
            return true;
        }

        return false;
    }

    static String updateCartouche(final String content) {
        // Trouver le début du cartouche
        int cartoucheStart = content.indexOf(CARTOUCHE_START_MARKER_1);
        if (cartoucheStart == -1) {
            isUsingCartouche1 = false;
            cartoucheStart = content.indexOf(CARTOUCHE_MARKER_2);
        }
        if (cartoucheStart == -1) {
            return content;
        }
        // Trouver la fin du cartouche
        int cartoucheEnd = content.indexOf(
                (isUsingCartouche1) ? CARTOUCHE_END_MARKER_1 : CARTOUCHE_MARKER_2, cartoucheStart);
        if (cartoucheEnd == -1) {
            return content;
        }
        cartoucheEnd += ((isUsingCartouche1) ? CARTOUCHE_END_MARKER_1 : CARTOUCHE_MARKER_2).length();

        final String cartouche = content.substring(cartoucheStart, cartoucheEnd);
        final String[] lines = cartouche.split(LINE_BREAK);

        // Trouver la dernière ligne de version
        String lastVersionLine = null;
        String lastSubVersion = null;

        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].contains("++MAJ") || lines[i].contains("++CRT")) {
                lastVersionLine = lines[i];
                // Extraire la sous-version
                final Matcher matcher = VERSION_PATTERN.matcher(lines[i]);
                if (matcher.find()) {
                    lastSubVersion = matcher.group();
                    break;
                }
            }
        }

        if (lastVersionLine == null || lastSubVersion == null) {
            return content;
        }

        // Incrémenter la sous-version
        final String newSubVersion = incrementSubVersion(lastSubVersion);
        new_sub_version = newSubVersion;

        // Créer la nouvelle ligne
        final String newLine = "** ++MAJ " + VERSION + " - " + newSubVersion + " - " + TRIGRAMME + " - " + DATE + " - " + MESSAGE;

        // Insérer la nouvelle ligne avant la dernière ligne du cartouche
        final StringBuilder newCartouche = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            newCartouche.append(lines[i]);
            if (i < lines.length - 1) {
                newCartouche.append(LINE_BREAK);
            }
            // Insérer avant la dernière ligne (qui est la ligne de séparation)
            if (i == lines.length - 2) {
                newCartouche.append(newLine).append(LINE_BREAK);
            }
        }

        // Remplacer l'ancien cartouche par le nouveau
        return content.substring(0, cartoucheStart) + newCartouche + content.substring(cartoucheEnd);
    }

    static String incrementSubVersion(final String subVersion) {
        // Pattern pour détecter les versions au format Lettre + chiffres + . + chiffres (1 à 3 chiffres)
        final Pattern pattern = Pattern.compile("([A-Z])(\\d+)\\.(\\d{1,3})");
        final Matcher matcher = pattern.matcher(subVersion);

        if (matcher.matches()) {
            final String letter = matcher.group(1);

            // Si la lettre n'est pas 'D', on retourne D1.000
            if (!"D".equals(letter)) {
                return "D1.000";
            }

            final String mainNum = matcher.group(2);

            // Convertir la partie numérique en entier
            int subNumInt;
            try {
                subNumInt = Integer.parseInt(matcher.group(3));
            } catch (final NumberFormatException e) {
                return "D1.000";
            }

            // Incrémenter
            subNumInt++;

            // Toujours formater avec 3 chiffres
            final String newSubNum = String.format("%03d", subNumInt);

            return letter + mainNum + "." + newSubNum;
        } else {
            // Si le format ne correspond pas (ex: H1_01), retourner D1.000
            return "D1.000";
        }
    }

    static String updateOnVersionMethod(final String content) {
        final Matcher matcher = ON_VERSION_PATTERN.matcher(content);
        final StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            // Trouver la dernière sous-version dans le cartouche pour la mettre à jour
            final String newSubVersion = new_sub_version;
            if (newSubVersion != null) {
                matcher.appendReplacement(result, "$1" + newSubVersion + "$3");
            } else {
                matcher.appendReplacement(result, matcher.group());
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    static String updateStaticVersion(final String content) {
        final Matcher matcher = STATIC_VERSION_PATTERN.matcher(content);
        final StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            // Trouver la dernière sous-version dans le cartouche pour la mettre à jour
            final String newSubVersion = findLastSubVersionInCartouche(content);
            if (newSubVersion != null) {
                matcher.appendReplacement(result, "$1" + newSubVersion + "$3");
            } else {
                matcher.appendReplacement(result, matcher.group());
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    static String findLastSubVersionInCartouche(final String content) {
        final int cartoucheStart = content.indexOf(
                isUsingCartouche1 ? CARTOUCHE_START_MARKER_1 : CARTOUCHE_MARKER_2);
        if (cartoucheStart == -1) {
            return null;
        }
        final int cartoucheEnd = content.indexOf(
                isUsingCartouche1 ? CARTOUCHE_END_MARKER_1 : CARTOUCHE_MARKER_2, cartoucheStart);
        if (cartoucheEnd == -1) {
            return null;
        }

        final String cartouche = content.substring(cartoucheStart, cartoucheEnd);
        final String[] lines = cartouche.split(LINE_BREAK);

        // Trouver la dernière sous-version dans le cartouche
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].contains("++MAJ") || lines[i].contains("++CRT")) {
                final Matcher matcher = VERSION_PATTERN.matcher(lines[i]);
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return null;
    }
}

