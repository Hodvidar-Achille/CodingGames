package com.hodvidar.miscellaneous.livecoding.codereview;

import java.io.*;
import java.util.*;

public class FileReaderUtil {

    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            e.printStackTrace();
        } finally {
            lines.clear();
        }
        return lines;
    }
}
