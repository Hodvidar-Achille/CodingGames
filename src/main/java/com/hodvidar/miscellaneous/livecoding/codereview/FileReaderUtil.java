package com.hodvidar.miscellaneous.livecoding.codereview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

    public List<String> readFile(final String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            // Add expection personalisée and throw // or log
            throw e;
        } finally {
            lines.clear();
        }
    }
}
