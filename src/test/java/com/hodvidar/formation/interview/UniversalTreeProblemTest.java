package com.hodvidar.formation.interview;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UniversalTreeProblemTest {


    private static Stream<Arguments> generateData_treeValue() {
        /**
         *            A
         *      A1           A2
         *  A1a    A1b   A2a    A2b
         *  all value == 3
         *  --> 7 unival (A1a, A1b, A2a, A2b,
         *  {A1,A1a,A1b}, {A2,A2a,A2b},
         *  {A, A1, A2, A1a, A1b, A2a, A2b})
         */
        final UniversalTreeProblem.TreeNode<Integer> nodeA1a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeA1b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeA2a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeA2b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeA1 = new UniversalTreeProblem.TreeNode<>(3, nodeA1a, nodeA1b);
        final UniversalTreeProblem.TreeNode<Integer> nodeA2 = new UniversalTreeProblem.TreeNode<>(3, nodeA2a, nodeA2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeA = new UniversalTreeProblem.TreeNode<>(3, nodeA1, nodeA2);
        final UniversalTreeProblem.Tree tree1 = new UniversalTreeProblem.Tree(nodeA);
        /**
         *              B=3
         *      B1=0            B2 = 3
         *  B1a=3    B1b=3   B2a=3    B2b= 3
         */
        final UniversalTreeProblem.TreeNode<Integer> nodeB1a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeB1b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeB2a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeB2b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeB1 = new UniversalTreeProblem.TreeNode<>(0, nodeB1a, nodeB1b);
        final UniversalTreeProblem.TreeNode<Integer> nodeB2 = new UniversalTreeProblem.TreeNode<>(3, nodeB2a, nodeB2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeB = new UniversalTreeProblem.TreeNode<>(3, nodeB1, nodeB2);
        final UniversalTreeProblem.Tree tree2 = new UniversalTreeProblem.Tree(nodeB);
        /**
         *              C=3
         *      C1=0            C2=0
         *  C1a=3    C1b=3   C2a=3    C2b=3
         */
        final UniversalTreeProblem.TreeNode<Integer> nodeC1a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeC1b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeC2a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeC2b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeC1 = new UniversalTreeProblem.TreeNode<>(0, nodeC1a, nodeC1b);
        final UniversalTreeProblem.TreeNode<Integer> nodeC2 = new UniversalTreeProblem.TreeNode<>(0, nodeC2a, nodeC2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeC = new UniversalTreeProblem.TreeNode<>(3, nodeC1, nodeC2);
        final UniversalTreeProblem.Tree<Integer> tree3 = new UniversalTreeProblem.Tree(nodeC);
        /**
         *            D
         *      D1           D2
         *  D1a    D1b   D2a    D2b
         *                     ø    E=3
         *                 E1=0            E2 = 3
         *             E1a=3    E1b=3   E2a=3    E2b= 3
         **/
        final UniversalTreeProblem.TreeNode<Integer> nodeE1a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeE1b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeE2a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeE2b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeE1 = new UniversalTreeProblem.TreeNode<>(0, nodeE1a, nodeE1b);
        final UniversalTreeProblem.TreeNode<Integer> nodeE2 = new UniversalTreeProblem.TreeNode<>(3, nodeE2a, nodeE2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeE = new UniversalTreeProblem.TreeNode<>(3, nodeE1, nodeE2);
        final UniversalTreeProblem.TreeNode<Integer> nodeD1a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeD1b = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeD2a = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeD2b = new UniversalTreeProblem.TreeNode<>(3, null, nodeE);
        final UniversalTreeProblem.TreeNode<Integer> nodeD1 = new UniversalTreeProblem.TreeNode<>(3, nodeD1a, nodeD1b);
        final UniversalTreeProblem.TreeNode<Integer> nodeD2 = new UniversalTreeProblem.TreeNode<>(3, nodeD2a, nodeD2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeD = new UniversalTreeProblem.TreeNode<>(3, nodeD1, nodeD2);
        final UniversalTreeProblem.Tree tree4 = new UniversalTreeProblem.Tree(nodeD);
        /**
         *     F
         *         F1
         *              F1a
         *                  F1ai
         *   F1ai = 1
         *   F1a+F1ai = 1
         *   F1+F1a+F1ai = 1
         *   F+F1+F1a+F1ai = 1
         *   =4
         */
        final UniversalTreeProblem.TreeNode<Integer> nodeF1ai = new UniversalTreeProblem.TreeNode<>(3);
        final UniversalTreeProblem.TreeNode<Integer> nodeF1a = new UniversalTreeProblem.TreeNode<>(3, null, nodeF1ai);
        final UniversalTreeProblem.TreeNode<Integer> nodeF1 = new UniversalTreeProblem.TreeNode<>(3, null, nodeF1a);
        final UniversalTreeProblem.TreeNode<Integer> nodeF = new UniversalTreeProblem.TreeNode<>(3, null, nodeF1);
        final UniversalTreeProblem.Tree tree5 = new UniversalTreeProblem.Tree(nodeF);
        /**
         *      G=0
         *   G1=1    G2=0
         *        G2a=1   G2b=0
         *     G2ai=1 G2aii=1
         *
         *  unival: G1  G2b  G2ai  G2ai and {G2a,G2ai,G2aii}  = 5
         */
        final UniversalTreeProblem.TreeNode<Integer> nodeG2ai = new UniversalTreeProblem.TreeNode<>(1);
        final UniversalTreeProblem.TreeNode<Integer> nodeG2aii = new UniversalTreeProblem.TreeNode<>(1);
        final UniversalTreeProblem.TreeNode<Integer> nodeG1 = new UniversalTreeProblem.TreeNode<>(1);
        final UniversalTreeProblem.TreeNode<Integer> nodeG2b = new UniversalTreeProblem.TreeNode<>(0);
        final UniversalTreeProblem.TreeNode<Integer> nodeG2a = new UniversalTreeProblem.TreeNode<>(1, nodeG2ai, nodeG2aii);
        final UniversalTreeProblem.TreeNode<Integer> nodeG2 = new UniversalTreeProblem.TreeNode<>(0, nodeG2a, nodeG2b);
        final UniversalTreeProblem.TreeNode<Integer> nodeG = new UniversalTreeProblem.TreeNode<>(3, nodeG1, nodeG2);
        final UniversalTreeProblem.Tree tree6 = new UniversalTreeProblem.Tree(nodeG);
        return Stream.of(
                Arguments.of(
                        tree1,
                        7
                ),
                Arguments.of(
                        tree2,
                        5
                ),
                Arguments.of(
                        tree3,
                        4
                ),
                Arguments.of(
                        tree4,
                        9
                ),
                Arguments.of(
                        tree5,
                        4
                ),
                Arguments.of(
                        tree6,
                        5
                )
        );
    }

    @ParameterizedTest
    @MethodSource("generateData_treeValue")
    public void numberOfUniversalValue(final UniversalTreeProblem.Tree<Integer> tree, final int expectedNumber) {
        assertThat(UniversalTreeProblem.numberOfNonEmptyUniversalValueTree(tree)).isEqualTo(expectedNumber);
    }
}
