package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day04_Test extends AbstractTestForAdventOfCode2020 {

    public _Day04_Test() {
        super(new _Day04());
    }

    @Override
    protected int getExpectedResult() {
        return 228;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm = true",
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929 = false",
            "hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm = true",
            "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in = false",
            "byr:blablabla = false",
            "iyr:blablabla byr:blablabla = false",
            "eyr:blablabla iyr:blablabla byr:blablabla = false",
            "hgt:blablabla eyr:blablabla iyr:blablabla byr:blablabla = false",
            "hcl:blablabla hgt:blablabla eyr:blablabla iyr:blablabla byr:blablabla = false",
            "ecl:blablabla hcl:blablabla hgt:blablabla eyr:blablabla iyr:blablabla byr:blablabla = false",
            "pid:blablabla ecl:blablabla hcl:blablabla hgt:blablabla eyr:blablabla iyr:blablabla byr:blablabla = true",
            "cid:blablabla = false",
    })
    void checkPassportData(final String passportData, final boolean isValid) throws FileNotFoundException {
        final boolean result = new _Day04().isValid(passportData);
        assertThat(result).isEqualTo(isValid);
    }

    @Test
    void checkFile() throws FileNotFoundException {
        final Scanner sc = getScanner(1);
        final int result = new _Day04().numberOfValidPassport(sc);
        assertThat(result).isEqualTo(2);
    }

}
