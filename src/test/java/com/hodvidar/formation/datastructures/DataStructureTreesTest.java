package com.hodvidar.formation.datastructures;

import com.hodvidar.utils.file.Constance;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DataStructureTreesTest {


    /**
     * 100 random numbers between -200 and 200
     */
    private static final int[] randomNumbers = new int[]{-31, -27, 87, 8, 177, -105, -99, -16, -124, 50, -19, 45, 36,
            101, -8, 39, -140, 61, 64, 137, 14, -111, -129, 106, 32, 34, 0, 37, 7, -56, 94, 56, -98, -6, -11, 68, 169,
            40, 22, 57, 2, 101, -125, -89, -65, 37, -4, 122, 71, -64, -40, -76, 72, -153, -39, 41, -157, -116, -118, 7,
            -12, -56, -6, -17, -37, 57, 134, 13, 90, 22, -64, 141, -86, -33, -32, 11, 17, 58, 23, -100, -62, -74, 82,
            25, -117, 165, 78, 73, 128, -62, -37, -131, -28, -46, -120, -102, 63, 61, -103, -18};

    private static String fileToStringContent(final String fileName) {
        try {
            final InputStream inputStream = new FileInputStream(fileName);
            final InputStreamReader isReader = new InputStreamReader(inputStream);
            final BufferedReader reader = new BufferedReader(isReader);
            final StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void init(final BinaryTree binaryTree) {
        for (final int i : randomNumbers) {
            binaryTree.insert(i);
        }
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

    @Test
    public void contains() {
        final BinaryTree binaryTree = new BinaryTree();
        init(binaryTree);
        assertThat(binaryTree.contains(25)).isTrue();
    }

    @Test
    public void maxDepth() {
        final BinaryTree binaryTree = new BinaryTree();
        init(binaryTree);
        assertThat(binaryTree.getMaxDepth()).isEqualTo(13);
    }

    @Test
    public void string_representation_simple() {
        final BinaryTree binaryTree = new BinaryTree();
        final int[] randomNumbers = new int[]{0, -10, 10, -20, -5, 5, 20, 100, -9, 99};
        for (final int i : randomNumbers) {
            binaryTree.insert(i);
        }
        assertThat(binaryTree.toString()).isEqualTo("                                                      {0}                                                       \n" +
                "                         {-10}                                                    {10}                          \n" +
                "           {-20}                        {-5}                        {5}                         {20}            \n" +
                "     {-}           {-}           {-9}          {-}           {-}           {-}           {-}          {100}     \n" +
                "  {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}    {-}   {99}    {-}  \n");
    }

    @Test
    @Disabled // TODO make it pass
    public void string_representation() {
        final BinaryTree binaryTree = new BinaryTree();
        init(binaryTree);
        final String fileName = Constance.RESOURCES_TEST
                + File.separator + "formation" + File.separator
                + "datastructure" + File.separator + "binaryTreeToString.txt";
        assertThat(binaryTree.toString()).isEqualTo(fileToStringContent(fileName));
    }

    @ParameterizedTest
    @MethodSource("generateData_treeValue")
    public void check_reverse_array(final int[] treeValues, final int expectedDepth) throws FileNotFoundException {
        final BinaryTree binaryTree = new BinaryTree();
        for (final int i : treeValues) {
            binaryTree.insert(i);
        }
        assertThat(binaryTree.getMaxDepth()).isEqualTo(expectedDepth);
    }
}
