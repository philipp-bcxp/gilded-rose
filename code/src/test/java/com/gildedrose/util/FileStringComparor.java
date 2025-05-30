package com.gildedrose.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileStringComparor {

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private static void printHunk(List<String> stringLines, List<String> fileLines,
                                  int start, int end, int contextLines) {
        // Calculate actual file line numbers for hunk header
        int fileStart = start + 1;
        int fileCount = end - start + 1;
        int stringStart = start + 1;
        int stringCount = end - start + 1;

        // Hunk header
        System.out.println(BOLD + CYAN + "@@ -" + fileStart + "," + fileCount +
            " +" + stringStart + "," + stringCount + " @@" + RESET);

        for (int i = start; i <= end; i++) {
            String stringLine = i < stringLines.size() ? stringLines.get(i) : null;
            String fileLine = i < fileLines.size() ? fileLines.get(i) : null;

            if (stringLine == null && fileLine != null) {
                // Line only in file (removed in string)
                System.out.println(RED + "-" + fileLine + RESET);
            } else if (fileLine == null && stringLine != null) {
                // Line only in string (added)
                System.out.println(GREEN + "+" + stringLine + RESET);
            } else if (stringLine != null && fileLine != null) {
                if (stringLine.equals(fileLine)) {
                    // Context line (unchanged)
                    System.out.println(" " + stringLine);
                } else {
                    // Changed line
                    System.out.println(RED + "-" + fileLine + RESET);
                    System.out.println(GREEN + "+" + stringLine + RESET);
                }
            }
        }
    }

    public static void compareStringWithFile(String inputString, String filePath) throws IOException {
        // Read file content from resources
        List<String> fileLines = readFileFromResources(filePath);
        String fileContent = String.join("\n", fileLines);

        // Simple comparison
        if (inputString.equals(fileContent)) {
            System.out.println("No differences found.");
            return;
        }




        // Line-by-line comparison
        List<String> stringLines = Arrays.asList(inputString.split("\n"));

        System.out.println(BOLD + RED + "--- a/Test Fixture" + RESET);
        System.out.println(BOLD + GREEN + "+++ b/Output from Programm" + RESET);

        int contextLines = 3; // Number of context lines to show
        int maxLines = Math.max(stringLines.size(), fileLines.size());

        // Find all different line indices
        List<Integer> diffLines = new ArrayList<>();
        for (int i = 0; i < maxLines; i++) {
            String stringLine = i < stringLines.size() ? stringLines.get(i) : null;
            String fileLine = i < fileLines.size() ? fileLines.get(i) : null;

            if ((stringLine == null && fileLine != null) ||
                (stringLine != null && fileLine == null) ||
                (stringLine != null && fileLine != null && !stringLine.equals(fileLine))) {
                diffLines.add(i);
            }
        }

        // Group consecutive differences into hunks
        if (!diffLines.isEmpty()) {
            int hunkStart = Math.max(0, diffLines.get(0) - contextLines);
            int hunkEnd = hunkStart;

            for (int diffLineIndex : diffLines) {
                if (diffLineIndex > hunkEnd + contextLines * 2) {
                    // Print current hunk and start a new one
                    printHunk(stringLines, fileLines, hunkStart, hunkEnd, contextLines);
                    hunkStart = Math.max(0, diffLineIndex - contextLines);
                }
                hunkEnd = Math.min(maxLines - 1, diffLineIndex + contextLines);
            }

            // Print the last hunk
            printHunk(stringLines, fileLines, hunkStart, hunkEnd, contextLines);
        }

        System.out.println(BOLD + RED + "--- a/Test Fixture" + RESET);
        System.out.println(BOLD + GREEN + "+++ b/Output from Programm" + RESET);
    }


    // Method to read file from resources folder
    private static List<String> readFileFromResources(String fileName) throws IOException {
        ClassLoader classLoader = TexttestFixture.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList());
        }
    }





}
