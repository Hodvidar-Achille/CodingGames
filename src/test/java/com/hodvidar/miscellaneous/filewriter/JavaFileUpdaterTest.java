package com.hodvidar.miscellaneous.filewriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFileUpdaterTest {

    @BeforeEach
    void setUp() {
        JavaFileUpdater.initializeStaticVariables();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "D1.011 | D1.012",
            "D1.000 | D1.001",
            "D1.01 | D1.002",
            "B1.002 | D1.000",
            "B1.00 | D1.000"
    })
    void incrementSubVersion(final String input, final String expectedResult) {
        assertThat(JavaFileUpdater.incrementSubVersion(input)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "** ++MAJ H5_01 - D1.011 - FAH - 14.02.19 - Modification select onExeQuery() car prise en compte de estamimo | D1.012",
            "** ++CRT J1_01 - D1.000 - FAH - 13.02.23 - Creation Qualiac | D1.001",
            "** ++MAJ : H1_01   - DEB - D1.01 - 06.03.2015 - Optimisation Java pour noyau HTML 5 | D1.002",
            "** ++MAJ C3_01 - B1.002 - MLC - 29.04.02 - Possibilite d'appliquer les modifs instantanément (persistant) | D1.000",
            "** ++CRT  B1.00 - 15.05.2002 - RF - Création QUALIAC | D1.000"
    })
    void updateCartouche(final String input, final String expectedResult) {
        final String start = "/* =============================================================================" + "\n";
        final String end = "============================================================================= */" + "\n";
        final String line1 = "** ++MAJ blabla" + "\n";
        final String line2 = "** ++MAJ blabla - D1.101" + "\n";
        final String inputCartouche = start + line1 + line2 + input + "\n" + end;
        final String expectedCartouche = start + line1 + line2 + input + "\n"
                + "** ++MAJ " + JavaFileUpdater.VERSION + " - " + expectedResult + " - " + JavaFileUpdater.TRIGRAMME
                + " - " + JavaFileUpdater.DATE + " - " + JavaFileUpdater.MESSAGE + "\n" + end;
        assertThat(JavaFileUpdater.updateCartouche(inputCartouche)).isEqualTo(expectedCartouche);
    }


    @Test
    void updateOnVersionMethod_shouldUpdateSimpleVersion() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.000
                ** ++MAJ blabla - D1.001
                ============================================================================= */
                
                
                @Override
                public String onVersion(IDefaultEvent event) {
                    return "D1.001";
                }
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateOnVersionMethod(updatedCartouche);

        // Assert
        assertThat(result).contains("return \"D1.002\";"); // Dernière version trouvée dans le test précédent
    }

    @Test
    void updateOnVersionMethod_shouldUpdateWithDifferentSpacing() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla - B1.001
                ** ++MAJ blabla - B1.002
                ============================================================================= */
                
                public String onVersion(IDefaultEvent e) {
                    // Some comment
                    return "B1.002";
                }
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateOnVersionMethod(updatedCartouche);

        // Asser
        assertThat(result).contains("return \"D1.000\";"); // 'B' devient 'D1.000'
    }

    @Test
    void updateOnVersionMethod_shouldNotChangeIfNoMatch() {
        // Arrange
        final String content = """
                /* =============================================================================
                 ** ++MAJ blabla
                 ** ++MAJ blabla - D1.000
                 ** ++MAJ blabla - D1.001
                 ============================================================================= */
                
                 public String someOtherMethod() {
                     return "something else";
                 }
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateOnVersionMethod(updatedCartouche);

        // Assert
        assertThat(result).contains("return \"something else\";");
    }


    @Test
    void updateStaticVersion_shouldUpdatePublicStaticFinalVersion() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.002
                ** ++MAJ blabla - D1.003
                ============================================================================= */
                
                public static final String VERSION = "D1.003";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("public static final String VERSION = \"D1.004\";");
    }

    @Test
    void updateStaticVersion_shouldUpdatePrivateVersion() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla
                ** ++MAJ blabla - B1.00
                ============================================================================= */
                
                private String VERSION = "B1.00";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("private String VERSION = \"D1.000\";");
    }

    @Test
    void updateStaticVersion_shouldUpdateProtectedStaticVersion() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.01
                ============================================================================= */
                
                protected static String VERSION = "D1.01";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("protected static String VERSION = \"D1.002\";");
    }

    @Test
    void updateStaticVersion_shouldUpdateVersionWithoutModifier() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.000
                ============================================================================= */
                
                final String VERSION = "D1.000";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("final String VERSION = \"D1.001\";");
    }

    @Test
    void updateStaticVersion_shouldUpdateStaticVersionWithoutModifier() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.004
                ============================================================================= */
                
                private static String version = "D1.004";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("private static String version = \"D1.005\";");
    }

    @Test
    void updateStaticVersion_shouldNotChangeOtherVariables() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++MAJ blabla
                ** ++MAJ blabla
                ** ++MAJ blabla - D1.005
                ============================================================================= */
                
                private String NAME = "Test";
                public static final String VERSION = "D1.005";
                protected String otherVariable = "value";
                """;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(content);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);

        // Assert
        assertThat(result).contains("public static final String VERSION = \"D1.006\";");
        assertThat(result).contains("private String NAME = \"Test\";"); // Inchangé
        assertThat(result).contains("protected String otherVariable = \"value\";"); // Inchangé
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "public static final String VERSION = \"D1.003\"; | D1.003 | D1.004",
            "private String VERSION = \"B1.00\"; | B1.00 | D1.000",
            "protected static final String VERSION = \"D1.01\"; | D1.01 | D1.002",
            "String VERSION = \"D1.000\"; | D1.000 | D1.001",
            "public final String VERSION = \"D1.055\"; | D1.055 | D1.056",
            "public final String version = \"B1.055\"; | B1.055 | D1.000"
    })
    void updateStaticVersion_parameterizedTests(final String input, final String previousSubVersion, final String expectedSubVersion) {
        // Arrange
        final String endCartouche = "============================================================================= */";
        final String cartoucheContent = "/* =============================================================================\n" +
                "** ++MAJ H5_01 - " + previousSubVersion + " - FAH - 14.02.19\n"
                + endCartouche + "\n"
                + "\n\n" + input;

        // Act
        final String updatedCartouche = JavaFileUpdater.updateCartouche(cartoucheContent);
        final String result = JavaFileUpdater.updateStaticVersion(updatedCartouche);
        final String resultWithoutCartouche = result.substring(result.indexOf(endCartouche)+endCartouche.length());
        // Assert
        assertThat(resultWithoutCartouche).contains(expectedSubVersion);
    }

    @Test
    void updateStaticVersion_shouldNotChangeIfNoVersionVariable() {
        // Arrange
        final String content = """
                public class Test {
                    private String name;
                    public void method() {}
                }
                """;

        // Act
        final String result = JavaFileUpdater.updateStaticVersion(content);

        // Assert
        assertThat(result).isEqualTo(content);
    }

    @Test
    void updateContent1() {
        // Arrange
        final String content = """
                /* =============================================================================
                ** ++PRG Amceva.java
                ** ++CRT XX_XX - D1.000 - BFR - 19.01.15 - Creation Qualiac
                ** ++MAJ H1_01 - D1.001 - DEB - 10.03.15 - Optimisation Java pour noyau HTML 5 : supp variables membres
                ** ++MAJ H1_01 -                           Suppr onActivate,onPostPrevRec,onPostNextRec et onPostSynchro
                ** ++MAJ H4_01 - D1.012 - DEB - 17.01.18 - Maj commentaires
                ** ++MAJ J4_01 - D1.013 - DEB - 17.01.18 - Maj commentaires
                ============================================================================= */
                
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest1 {
                
                    private static String version = "D1.013";
                
                
                        @Override
                    public String onVersion(IDefaultEvent event) {
                        return "D1.013";
                    }
                }
                
                """;

        final String expectedResult = """
                /* =============================================================================
                ** ++PRG Amceva.java
                ** ++CRT XX_XX - D1.000 - BFR - 19.01.15 - Creation Qualiac
                ** ++MAJ H1_01 - D1.001 - DEB - 10.03.15 - Optimisation Java pour noyau HTML 5 : supp variables membres
                ** ++MAJ H1_01 -                           Suppr onActivate,onPostPrevRec,onPostNextRec et onPostSynchro
                ** ++MAJ H4_01 - D1.012 - DEB - 17.01.18 - Maj commentaires
                ** ++MAJ J4_01 - D1.013 - DEB - 17.01.18 - Maj commentaires
                ** ++MAJ       - D1.014 - AGE - 13.01.26 - Fix nombre instances String
                ============================================================================= */
                
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest1 {
                
                    private static String version = "D1.014";
                
                
                        @Override
                    public String onVersion(IDefaultEvent event) {
                        return "D1.014";
                    }
                }
                
                """;

        // Act
        final String result = JavaFileUpdater.updateContent(content);
        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void updateContent2() {
        // Arrange
        final String content = """
                // =============================================================================
                // ++PRG BudgetCategoriesImpl.java
                // ++CRT H6_01 - PFR - D1.000 - 21.10.19
                // ++CRT H6_01 - PFR - D1.001 - 21.10.19
                // ++CRT H6_01 - PFR - B1.055 - 21.10.19
                // =============================================================================
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest2 {
                
                    public final String version = "B1.055";
                
                
                        @Override
                    public String onVersion(IDefaultEvent event) {
                        return "B1.055";
                    }
                
                }
                
                """;

        final String expectedResult = """
                // =============================================================================
                // ++PRG BudgetCategoriesImpl.java
                // ++CRT H6_01 - PFR - D1.000 - 21.10.19
                // ++CRT H6_01 - PFR - D1.001 - 21.10.19
                // ++CRT H6_01 - PFR - B1.055 - 21.10.19
                // ++MAJ J4_01 - D1.000 - AGE - 13.01.26 - Fix nombre instances String
                // =============================================================================
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest2 {
                
                    public final String version = "D1.000";
                
                
                        @Override
                    public String onVersion(IDefaultEvent event) {
                        return "D1.000";
                    }
                
                }
                
                """;

        // Act
        final String result = JavaFileUpdater.updateContent(content);
        // Assert
        assertThat(result).isEqualTo(expectedResult);
        assertThat(JavaFileUpdater.numberOfCartouchesModified).isEqualTo(1);
        assertThat(JavaFileUpdater.numberOfMethodsModified).isEqualTo(1);
        assertThat(JavaFileUpdater.numberOfMethodsModified).isEqualTo(1);
    }

    @Test
    void updateContent3() {
        // Arrange
        final String content = """
                // =============================================================================
                // ++PRG BudgetCategoriesImpl.java
                // ++CRT H6_01 - PFR - D1.000 - 21.10.19
                // ++CRT H6_01 - PFR - D1.001 - 21.10.19
                // ++CRT H6_01 - PFR - B1.055 - 21.10.19
                // =============================================================================
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest2 {
               
                    @Override
                    public String onVersion(IDefaultEvent event) {
                        return "B1.055";
                    }
                
                }
                
                """;

        final String expectedResult = """
                // =============================================================================
                // ++PRG BudgetCategoriesImpl.java
                // ++CRT H6_01 - PFR - D1.000 - 21.10.19
                // ++CRT H6_01 - PFR - D1.001 - 21.10.19
                // ++CRT H6_01 - PFR - B1.055 - 21.10.19
                // ++MAJ J4_01 - D1.000 - AGE - 13.01.26 - Fix nombre instances String
                // =============================================================================
                package com.hodvidar.miscellaneous.filewriter;
                
                public class FileWriterTest2 {
                
                    @Override
                    public String onVersion(IDefaultEvent event) {
                        return "D1.000";
                    }
                
                }
                
                """;

        // Act
        final String result = JavaFileUpdater.updateContent(content);
        // Assert
        assertThat(result).isEqualTo(expectedResult);
        assertThat(JavaFileUpdater.numberOfCartouchesModified).isEqualTo(1);
        assertThat(JavaFileUpdater.numberOfMethodsModified).isEqualTo(1);
        assertThat(JavaFileUpdater.numberOfVariablesModified).isEqualTo(0);
    }


}
