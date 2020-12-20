package com.hodvidar.adventofcode.y2020;

import java.util.Objects;

public class _Day04_2 extends _Day04 {

    public static void main(String[] args) throws Exception {
        _Day04_2 me = new _Day04_2();
        int result = me.numberOfValidPassport(me.getScanner());
        System.err.println("Expected '175' - result='" + result + "'");
    }

    /**
     * You can continue to ignore the cid field,
     * but each other field has strict rules about what values
     * are valid for automatic validation:
     * <p>
     * byr (Birth Year) - four digits; at least 1920 and at most 2002.
     * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
     * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
     * hgt (Height) - a number followed by either cm or in:
     * If cm, the number must be at least 150 and at most 193.
     * If in, the number must be at least 59 and at most 76.
     * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
     * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
     * pid (Passport ID) - a nine-digit number, including leading zeroes.
     * cid (Country ID) - ignored, missing or not.
     */
    @Override
    public boolean isValid(String passportData) {
        passportData = passportData.trim();
        if (!super.isValid(passportData)) {
            return false;
        }
        for (String field : passportData.split(" ")) {
            String fieldKey = field.substring(0, 4);
            String fieldValue = field.substring(4).trim();
            boolean isValid = switch (fieldKey) {
                case BIRTH_YEAR -> checkBirthYear(fieldValue);
                case ISSUE_YEAR -> checkIssueYear(fieldValue);
                case EXPIRATION_YEAR -> checkExpirationYear(fieldValue);
                case HEIGHT -> checkHeight(fieldValue);
                case HAIR_COLOR -> checkHairColor(fieldValue);
                case EYE_COLOR -> checkEyeColor(fieldValue);
                case PASSPORT_ID -> checkPassportID(fieldValue);
                case COUNTRY_ID -> true;
                default -> false;
            };
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    // byr (Birth Year) - four digits; at least 1920 and at most 2002.
    public boolean checkBirthYear(String birthYear) {
        try {
            int value = Integer.parseInt(birthYear);
            return value >= 1920 && value <= 2002;
        } catch (Throwable ex) {
            return false;
        }
    }

    // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    public boolean checkIssueYear(String issueYear) {
        try {
            int value = Integer.parseInt(issueYear);
            return value >= 2010 && value <= 2020;
        } catch (Throwable ex) {
            return false;
        }
    }

    // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    public boolean checkExpirationYear(String issueYear) {
        try {
            int value = Integer.parseInt(issueYear);
            return value >= 2020 && value <= 2030;
        } catch (Throwable ex) {
            return false;
        }
    }

    // hgt (Height) - a number followed by either cm or in:
    //     *      If cm, the number must be at least 150 and at most 193.
    //     *      If in, the number must be at least 59 and at most 76.
    public boolean checkHeight(String height) {
        try {
            int heightValue = Integer.parseInt(height.substring(0, height.length() - 2));
            if (height.contains("cm")) {
                return heightValue >= 150 && heightValue <= 193;
            } else if (height.contains("in")) {
                return heightValue >= 59 && heightValue <= 76;
            } else {
                return false;
            }
        } catch (Throwable ex) {
            return false;
        }
    }

    // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    public boolean checkHairColor(String hairColor) {
        String hairColorRegex = "^[0-9a-f]+$";
        try {
            if (!Objects.equals('#', hairColor.charAt(0))) {
                return false;
            }
            hairColor = hairColor.substring(1);
            return hairColor.matches(hairColorRegex);
        } catch (Throwable ex) {
            return false;
        }
    }

    // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    public boolean checkEyeColor(String eyeColor) {
        return Objects.equals("amb", eyeColor)
                || Objects.equals("blu", eyeColor)
                || Objects.equals("brn", eyeColor)
                || Objects.equals("gry", eyeColor)
                || Objects.equals("grn", eyeColor)
                || Objects.equals("hzl", eyeColor)
                || Objects.equals("oth", eyeColor);
    }

    // pid (Passport ID) - a nine-digit number, including leading zeroes.
    public boolean checkPassportID(String passportID) {
        try {
            String passportIdRegex = "^\\d{9}$";
            return passportID.matches(passportIdRegex);
        } catch (Throwable ex) {
            return false;
        }
    }

    // cid (Country ID) - ignored, missing or not.
}
