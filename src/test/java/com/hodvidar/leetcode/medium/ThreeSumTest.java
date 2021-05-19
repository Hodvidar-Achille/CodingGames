package com.hodvidar.leetcode.medium;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES_TEST;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeSumTest {

    private static int[] getLongInputArray() throws FileNotFoundException {
        final String inputFile = RESOURCES_TEST + File.separator + "leetcode" + File.separator + "threeSum.txt";
        final File file = new File(inputFile);
        final Scanner sc = new Scanner(file);
        String line;
        final List<String> instructions = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            instructions.add(line);
        }
        final String[] numbers = instructions.get(0).split(",");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
    }

    @Test
    public void threeSum_shortInput() {
        final int[] array = new int[]{-1, 0, 1, 2, -1, -4};
        final List<List<Integer>> actual = ThreeSum.threeSum(array);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).containsExactlyInAnyOrder(-1, 0, 1);
        assertThat(actual.get(1)).containsExactlyInAnyOrder(-1, -1, 2);
    }

    @Test
    public void threeSumFaster_shortInput() {
        final int[] array = new int[]{-1, 0, 1, 2, -1, -4};
        final List<List<Integer>> actual = ThreeSum.threeSumFaster(array);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(1)).containsExactlyInAnyOrder(-1, -1, 2);
        assertThat(actual.get(0)).containsExactlyInAnyOrder(-1, 0, 1);
    }

    @Test
    public void threeSumFaster2_shortInput() {
        final int[] array = new int[]{-1, 0, 1, 2, -1, -4};
        final List<List<Integer>> actual = ThreeSum.threeSumFaster2(array);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).containsExactlyInAnyOrder(-1, -1, 2);
        assertThat(actual.get(1)).containsExactlyInAnyOrder(-1, 0, 1);
    }

    @Test
    @Disabled
    public void threeSum_longInput() throws FileNotFoundException {
        final int[] array = getLongInputArray();
        final long before = System.currentTimeMillis();
        final List<List<Integer>> actual = ThreeSum.threeSum(array);
        final long after = System.currentTimeMillis();
        assertThat(actual.size()).isEqualTo(16258);
        assertThat(after - before).isLessThan(3000);
    }

    @Test
    public void threeSumFaster_longInput() throws FileNotFoundException {
        final int[] array = getLongInputArray();
        final long before = System.currentTimeMillis();
        final List<List<Integer>> actual = ThreeSum.threeSumFaster(array);
        final long after = System.currentTimeMillis();
        assertThat(actual.size()).isEqualTo(16258);
        assertThat(after - before).isLessThan(3000);
    }

    @Test
    public void threeSumFaster2_longInput() throws FileNotFoundException {
        final int[] array = getLongInputArray();
        final long before = System.currentTimeMillis();
        final List<List<Integer>> actual = ThreeSum.threeSumFaster2(array);
        final long after = System.currentTimeMillis();
        assertThat(actual.size()).isEqualTo(16258);
        assertThat(after - before).isLessThan(3000);
    }
}
