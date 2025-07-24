package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;

public class Day09p2 extends Day09 {

    @Override
    public List<String> compactDiskMap(final List<String> fileBlocks) {
        // Determine unique file IDs in decreasing order
        final List<String> uniqueFileIds = fileBlocks.stream()
                .filter(block -> !FREE_SPACE.equals(block)) // Exclude free spaces
                .distinct()
                .sorted((a, b) -> Integer.compare(Integer.parseInt(b), Integer.parseInt(a))) // Sort by decreasing file ID
                .toList();

        // Attempt to move each file ID in order
        for (final String fileId : uniqueFileIds) {
            moveNumber(fileBlocks, fileId);
        }

        return fileBlocks;
    }

    private void moveNumber(final List<String> fileBlocks, final String fileId) {
        // Find all positions of the current file ID
        final List<Integer> filePositions = new ArrayList<>();
        for (int i = 0; i < fileBlocks.size(); i++) {
            if (fileBlocks.get(i).equals(fileId)) {
                filePositions.add(i);
            }
        }

        final int fileSize = filePositions.size();
        if (fileSize == 0) {
            return; // No blocks of this file found
        }

        // Search for the first span of free spaces large enough to fit the file
        int freeSpaceStart = -1;
        int freeSpaceLength = 0;

        for (int i = 0; i < fileBlocks.size(); i++) {
            if (FREE_SPACE.equals(fileBlocks.get(i))) {
                if (freeSpaceStart == -1) {
                    freeSpaceStart = i;
                    if (freeSpaceStart > filePositions.getFirst()) {
                        return;
                    }
                }
                freeSpaceLength++;
            } else {
                freeSpaceStart = -1;
                freeSpaceLength = 0;
            }

            // If a suitable span is found
            if (freeSpaceLength >= fileSize) {
                // Move the file blocks to the new position
                for (int j = 0; j < fileSize; j++) {
                    fileBlocks.set(freeSpaceStart + j, fileId);
                }
                // Clear the original positions
                for (final int position : filePositions) {
                    fileBlocks.set(position, FREE_SPACE);
                }
                return; // File moved, stop searching
            }
        }
    }
}
