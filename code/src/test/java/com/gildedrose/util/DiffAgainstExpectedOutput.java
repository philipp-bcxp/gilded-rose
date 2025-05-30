package com.gildedrose.util;

import java.io.IOException;

import static com.gildedrose.util.FileStringComparor.compareStringWithFile;
import static com.gildedrose.util.TexttestFixture.executeGildedRose;

public class DiffAgainstExpectedOutput {
    public static void main(String[] args) {
        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }
        String gildedRoseOutput = executeGildedRose(days);

        // System.out.println(gildedRoseOutput);

        try {
            compareStringWithFile(gildedRoseOutput, "expected_output.txt");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
