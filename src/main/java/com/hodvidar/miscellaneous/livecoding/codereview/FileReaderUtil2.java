package com.hodvidar.miscellaneous.livecoding.codereview;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileReader2 {

    private FileReaderUtil() { /* utility class – prevent instantiation */ }

    /**
     * Reads the entire file located at {@code filePath} and returns a list
     * containing each line (without line‑termination characters).
     *
     * @param filePath absolute or relative path to the text file
     * @return an immutable list of lines; never {@code null}
     * @throws IOException if the file cannot be opened or read
     */
    public static List<String> readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        // The method itself handles opening/closing the underlying stream.
        // StandardCharsets.UTF_8 is explicit – change if you need another encoding.
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    /**
     * Alternative streaming version – useful for very large files
     * where you don’t want to load everything into memory at once.
     *
     * @param filePath path to the file
     * @return a {@link List} containing all lines (still loads everything,
     *         but demonstrates the safe streaming pattern)
     * @throws IOException if something goes wrong while reading
     */
    public static List<String> readFileStreaming(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<String> lines = new java.util.ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return java.util.Collections.unmodifiableList(lines);
        }
    }

}
