package com.hodvidar.miscellaneous.livecoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution3 {

    private Solution3() {
    }

    public static int getResult(final String input) {
        final Map<Double, List<Integer>> timeCallsByPhone = getListOfTimesByPhoneNumber(input);
        final Map<Double, Integer> totalTimeByPhone =
                timeCallsByPhone.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream().reduce(0, Integer::sum)
                        ));
        final Double numberWithMaxCallTime = getNumberWithMaxTime(totalTimeByPhone);
        timeCallsByPhone.remove(numberWithMaxCallTime);
        return timeCallsByPhone.values().stream()
                .map(list -> list.stream().mapToInt(Solution3::computePriceInCents).sum())
                .reduce(0, Integer::sum);
    }

    protected static Map<Double, List<Integer>> getListOfTimesByPhoneNumber(final String input) {
        final Map<Double, List<Integer>> timeCallsByPhone = new HashMap<>();
        for (final String phoneCall : input.split("\n")) {
            final String[] split = phoneCall.split(",");
            final String timeRaw = split[0];
            final String phoneNumber = split[1];
            final int timeInSeconds = parseTimeInSeconds(timeRaw);
            final Double phoneNumberAsNumber = phoneToNumber(phoneNumber);
            addCallInMap(timeCallsByPhone, phoneNumberAsNumber, timeInSeconds);
        }
        return timeCallsByPhone;
    }

    static Double getNumberWithMaxTime(final Map<Double, Integer> totalTimeByPhone) {
        Integer maxCallTime = 0;
        Double numberWithMaxCallTime = Double.MAX_VALUE;
        for (final Map.Entry<Double, Integer> entry : totalTimeByPhone.entrySet()) {
            final Double number = entry.getKey();
            final Integer totalTime = entry.getValue();
            if (totalTime > maxCallTime
                    || (totalTime.equals(maxCallTime) && number < numberWithMaxCallTime)) {
                maxCallTime = totalTime;
                numberWithMaxCallTime = number;
            }
        }
        return numberWithMaxCallTime;
    }

    static void addCallInMap(final Map<Double, List<Integer>> pricesByPhone,
                             final Double phoneNumber,
                             final int timeOfCall) {
        if (pricesByPhone.containsKey(phoneNumber)) {
            pricesByPhone.get(phoneNumber).add(timeOfCall);
        } else {
            final List<Integer> listOfTimes = new ArrayList<>();
            listOfTimes.add(timeOfCall);
            pricesByPhone.put(phoneNumber, listOfTimes);
        }
    }

    /**
     * "00:01:07 --> 67 seconds
     */
    static int parseTimeInSeconds(final String time) {
        final String[] split = time.split(":");
        final int hour = Integer.parseInt(split[0]);
        final int minute = Integer.parseInt(split[1]);
        final int second = Integer.parseInt(split[2]);
        return hour * 3600 + minute * 60 + second;
    }

    static int computePriceInCents(final int timeOfCall) {
        if (timeOfCall < 300) {
            return timeOfCall * 3;
        }
        return (int) (Math.ceil(timeOfCall / 60d) * 150);
    }

    /**
     * 400-234-090 --> 400234090
     * "400-234-090 = 400234090",
     * "701-080-080 = 701080080",
     * "001-001-001 = 1001001"
     */
    static Double phoneToNumber(final String phoneNumber) {
        return Double.valueOf(phoneNumber.replace("-", ""));
    }

}
