package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day04_2_Test extends AbstractTestForAdventOfCode {
    @Override
    protected int getDay() {
        return 4;
    }

    @Test
    void checkFile() throws FileNotFoundException {
        Scanner sc = getScanner(2);
        int result = new _Day04_2().numberOfValidPassport(sc);
        assertThat(result).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "eyr:1972 cid:100 ecl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926 = false",
            "iyr:2019 hcl:#602927 eyr:1967 hgt:170cm hcl:grn pid:012533040 byr:1946 = false",
            "hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277 = false",
            "hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007 = false",
            "------------------------------------------------- = false",
            "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f = true",
            "eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm = true",
            "hcl:#888785 hgt:164cm byr:2001 iyr:2015 cid:88 pid:545766238 ecl:hzl eyr:2022 = true",
            "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719 = true"
    })
    void checkPassportData(String passportData, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().isValid(passportData);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2002 = true",
            "2003 = false",
            "1918 = false",
            "100 = false",
            "XXXX = false"
    })
    void checkBirthYear(String birthYear, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkBirthYear(birthYear);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2015 = true",
            "2009 = false",
            "2021 = false",
            "100 = false",
            "XXXXXXX = false"
    })
    void checkIssueYear(String issueYear, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkIssueYear(issueYear);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2025 = true",
            "2019 = false",
            "2031 = false",
            "100 = false",
            "XXXXXXX = false"
    })
    void checkExpirationYear(String expirationYear, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkExpirationYear(expirationYear);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "175cm = true",
            "149cm = false",
            "194cm = false",
            "70in = true",
            "58in = false",
            "77in = false",
            "58 = false",
            "XXXXX = false",
            "XXXcm = false"
    })
    void checkHeight(String height, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkHeight(height);
        assertThat(result).isEqualTo(isValid);
    }

    // ParameterizedTest and CsvSource doesn't handle well the '#' character
    @Test
    void checkHairColor() throws FileNotFoundException {
        boolean result = new _Day04_2().checkHairColor("#afafaf");
        assertThat(result).isEqualTo(true);

        result = new _Day04_2().checkHairColor("#123456");
        assertThat(result).isEqualTo(true);

        result = new _Day04_2().checkHairColor("#abcdef");
        assertThat(result).isEqualTo(true);

        result = new _Day04_2().checkHairColor("#gggggg");
        assertThat(result).isEqualTo(false);

        result = new _Day04_2().checkHairColor("#XXXXXX");
        assertThat(result).isEqualTo(false);

        result = new _Day04_2().checkHairColor("XXX");
        assertThat(result).isEqualTo(false);

        result = new _Day04_2().checkHairColor("#");
        assertThat(result).isEqualTo(false);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "amb = true",
            "blu = true",
            "brn = true",
            "gry = true",
            "grn = true",
            "hzl = true",
            "oth = true",
            "othoth = false",
            "xxx = false",
            " = false"
    })
    void checkEyeColor(String birthYear, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkEyeColor(birthYear);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "000000000 = true",
            "000000001 = true",
            "000000002 = true",
            "100000000 = true",
            "999999999 = true",
            "123456789 = true",
            "000100000 = true",
            "abcdefghi = false",
            "xxx = false",
            " = false"
    })
    void checkPassportID(String birthYear, boolean isValid) throws FileNotFoundException {
        boolean result = new _Day04_2().checkPassportID(birthYear);
        assertThat(result).isEqualTo(isValid);
    }


}
