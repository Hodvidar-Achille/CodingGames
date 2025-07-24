package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 extends AbstractAdventOfCode2024 {

    public static final String FREE_SPACE = ".";

    public String transformDiskMapInToString(final String diskMap) {
        return String.join("", transformDiskMap(diskMap));
    }

    public List<String> transformDiskMap(final String diskMap) {
        final List<String> fileBlocks = new ArrayList<>();
        int fileId = 0; // File ID counter

        for (int i = 0; i < diskMap.length(); i++) {
            final int length = Character.getNumericValue(diskMap.charAt(i)); // Get the length of the current section

            if (i % 2 == 0) { // File section
                final int repeat = Math.max(0, length);
                for (int r = 0; r < repeat; r++) {
                    fileBlocks.add(String.valueOf(fileId));
                }
                // Append the file ID for the length of the file
                fileId++; // Increment the file ID
            } else { // Free space section
                final int repeat = Math.max(0, length);
                for (int r = 0; r < repeat; r++) {
                    fileBlocks.add(".");
                }
            }
        }
        return fileBlocks;
    }

    public String compactDiskMapIntoString(final List<String> fileBlocks) {
        return String.join("", compactDiskMap(fileBlocks));
    }

    public List<String> compactDiskMap(final List<String> fileBlocks) {
        boolean moved;
        do {
            moved = moveNumber(fileBlocks);
        } while (moved);
        return fileBlocks;
    }

    private boolean moveNumber(final List<String> fileBlocks) {
        final int length = fileBlocks.size();
        for (int i = length - 1; i > 0; i--) {
            final String c = fileBlocks.get(i);
            if (FREE_SPACE.equals(c)) {
                continue;
            }
            // Look for a space to put the number
            for (int j = 0; j < i; j++) {
                final String c2 = fileBlocks.get(j);
                if (!FREE_SPACE.equals(c2)) {
                    continue;
                }
                // c is not a point, c2 is a point let's swap
                Collections.swap(fileBlocks, i, j);
                return true;
            }
        }
        return false;
    }

    public double calculateChecksum(final List<String> compactFileBlocks) {
        double checksum = 0;
        // Iterate through the disk map
        for (int position = 0; position < compactFileBlocks.size(); position++) {
            final String block = compactFileBlocks.get(position);
            // Ignore free space blocks
            if (!FREE_SPACE.equals(block)) {
                final double fileId = Double.parseDouble(block);
                checksum += (double) position * fileId;
            }
        }
        return checksum;
    }


    @Override
    public double getDigitFromLine(final String line) {
        final List<String> fileBlocks = transformDiskMap(line);
        final List<String> compactFileBlocks = compactDiskMap(fileBlocks);
        return calculateChecksum(compactFileBlocks);
    }
}
