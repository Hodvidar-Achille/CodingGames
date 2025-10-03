package com.hodvidar.codingame.puzzles.hard;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Scanner;

class CodeVsZombiesTest {

    @ParameterizedTest
    @CsvSource({
            "0,0,3,4,5",
            "0,0,13,14,19",
            "10,10,13,14,5",
            "0, 0, 707, 707, 999",
    })
    void testPointDist(final int x1, final int y1, final int x2, final int y2, final int expected) {
        final Player.Point p1 = new Player.Point(x1, y1);
        final Player.Point p2 = new Player.Point(x2, y2);
        Assertions.assertThat(p1.dist(p2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0,1000,0,1000,1000,0",
            "0,0,500,500,1000,500,500",
            "0,0,1000,1000,1000,707,707"
    })
    void testStepTowards(final int x1, final int y1, final int x2, final int y2, final int step, final int expectedX, final int expectedY) {
        final Player.Point p1 = new Player.Point(x1, y1);
        final Player.Point p2 = new Player.Point(x2, y2);
        final Player.Point result = p1.stepTowardsAndStopAt(p2, step);
        Assertions.assertThat(result.x).isEqualTo(expectedX);
        Assertions.assertThat(result.y).isEqualTo(expectedY);
    }

    @ParameterizedTest
    @CsvSource({
            "2,40",
            "3,90",
            "10,1000",
            "12,1440"
    })
    void testKillValue(final int humansAlive, final int expected) {
        Assertions.assertThat(Player.ScoreHelper.killValue(humansAlive)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,40",
            "2,2,120",
            "3,2,240",
            "4,2,440",
            "5,2,760",
            "6,10,32000"
    })
    void testMultiKillScore(final int zombiesKilled, final int humansAlive, final long expected) {
        Assertions.assertThat(Player.ScoreHelper.multiKillScore(zombiesKilled, humansAlive)).isEqualTo(expected);
    }

    @Test
    void testCandidatePointBestScore() {
        final Player.Point p = new Player.Point(0, 0);
        final Player.CandidatePoint cp1 = new Player.CandidatePoint(p, 10, null);
        final Player.CandidatePoint cp2 = new Player.CandidatePoint(p, 20, null);
        final Player.CandidatePoint cpParent = new Player.CandidatePoint(p, 5, Arrays.asList(cp1, cp2));
        Assertions.assertThat(cpParent.getBestCandidateScore()).isEqualTo(20);
    }

    @Test
    void testGameLoopWithMockScanner() {
        // Simule une partie avec 1 humain et 1 zombie
        final String input = "8000 4500\n1\n1 1000 1000\n1\n1 12000 8000 12000 8000\n";
        final Scanner scanner = new Scanner(input);
        final Player.Point result = Player.gameLoop(scanner);
        Assertions.assertThat(result).isNotNull();
        // On peut tester la position attendue selon la logique
        Assertions.assertThat(result.x).isBetween(7050, 7150);
        Assertions.assertThat(result.y).isBetween(4000, 4100);
    }

    @Test
    void testGameLoopMultipleTurnsWithMockScanner() {
        // Simule 3 tours: Ash, 2 humains, 2 zombies (positions changent à chaque tour)
        final Scanner scanner = getScanner();
        // 1
        Player.Point result = Player.gameLoop(scanner);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.x).isBetween(7100, 7200);
        Assertions.assertThat(result.y).isBetween(4000, 4100);
        // 2
        result = Player.gameLoop(scanner);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.x).isBetween(9850, 9950);
        Assertions.assertThat(result.y).isBetween(5400, 5500);
        // 3
        result = Player.gameLoop(scanner);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.x).isBetween(10400, 10500);
        Assertions.assertThat(result.y).isBetween(5850, 5950);
    }

    private static Scanner getScanner() {
        final String input =
                // Tour 1
                "8000 4500\n2\n1 1000 1000\n2 15000 8000\n2\n1 12000 8000 12000 8000\n2 5000 2000 5000 2000\n" +
                        // Tour 2
                        "9000 5000\n2\n1 1100 1100\n2 14900 7900\n2\n1 11900 7900 11900 7900\n2 5100 2100 5100 2100\n" +
                        // Tour 3
                        "9500 5500\n2\n1 1200 1200\n2 14800 7800\n2\n1 11800 7800 11800 7800\n2 5200 2200 5200 2200\n";
        final Scanner scanner = new Scanner(input);
        return scanner;
    }
}
