package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day04 extends AbstractAdventOfCode2020 {

    protected static final String BIRTH_YEAR = "byr:";
    protected static final String ISSUE_YEAR = "iyr:";
    protected static final String EXPIRATION_YEAR = "eyr:";
    protected static final String HEIGHT = "hgt:";
    protected static final String HAIR_COLOR = "hcl:";
    protected static final String EYE_COLOR = "ecl:";
    protected static final String PASSPORT_ID = "pid:";
    protected static final String COUNTRY_ID = "cid:";

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        return numberOfValidPassport(sc);
    }

    public int numberOfValidPassport(final Scanner sc) {
        int validPassportCounter = 0;
        String passportData = "";
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            line = line.trim();
            if (!line.isEmpty()) {
                passportData += line + " ";
            } else {
                if (isValid(passportData)) {
                    validPassportCounter += 1;
                }
                passportData = "";
            }
        }
        return validPassportCounter;
    }

    public boolean isValid(final String passportData) {
        return passportData.contains(BIRTH_YEAR)
                && passportData.contains(ISSUE_YEAR)
                && passportData.contains(EXPIRATION_YEAR)
                && passportData.contains(HEIGHT)
                && passportData.contains(HAIR_COLOR)
                && passportData.contains(EYE_COLOR)
                && passportData.contains(PASSPORT_ID)
                /* && passportData.contains(COUNTRY_ID) */;
    }

}

