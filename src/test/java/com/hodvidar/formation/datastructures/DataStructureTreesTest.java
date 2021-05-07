package com.hodvidar.formation.datastructures;

import com.hodvidar.hackerrank.ArrayReverse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DataStructureTreesTest {


    @Test
    public void contains() {
        BinaryTree binaryTree = new BinaryTree();
        init(binaryTree);
        BinaryTreeNode n = binaryTree.getRoot().getRight().getLeft();
        assertThat(binaryTree.contains(n.getValue())).isTrue();
    }

    @Test
    public void string_representation() {
        BinaryTree binaryTree = new BinaryTree();
        init(binaryTree);
        System.out.println(binaryTree.toString());
    }

    private static void init(BinaryTree binaryTree) {
        for (int i = 0; i < 100; i++) {
            binaryTree.insert(getRandomValue());
        }
    }

    private static int getRandomValue() {
        Random random = new Random();
        int r = random.nextInt(200) - random.nextInt(200);
        return r;
    }

    @ParameterizedTest
    @MethodSource("generateData_treeValue")
    void check_reverse_array(final int[] treeValues, final int expectedDepth) throws FileNotFoundException {
        BinaryTree binaryTree = new BinaryTree();
        for(int i : treeValues) {
            binaryTree.insert(i);
        }
        assertThat(binaryTree.getMaxDepth()).isEqualTo(expectedDepth);
    }

    private static Stream<Arguments> generateData_treeValue() {
        return Stream.of(
                Arguments.of(
                        new int[]{1},
                        1
                ),
                Arguments.of(
                        new int[]{1, 2},
                        2
                ),
                Arguments.of(
                        new int[]{1, 2, 3},
                        3
                ),
                Arguments.of(
                        new int[]{1, 2, -1},
                        2
                ),
                Arguments.of(
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        10
                ),
                Arguments.of(
                        new int[]{1, -2, -3, -4, -5, -6, -7, -8, -9, -10},
                        10
                ),
                Arguments.of(
                        new int[]{1, 2, -3, 4, -5, 6, -7, 8, -9, 10},
                        6
                )
        );
    }
}
