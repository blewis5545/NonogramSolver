package com.butcher.nonogram;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class NonogramSolverTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void solve() {
    }

    @Test
    void buildLine_withSingleConstraint() {
        int boardSize = 5;
        CellValue[] expected = buildInput("11110");
        int[] constraints = new int[]{4};
        int[] spacing = new int[]{0, 1};

        CellValue[] result = NonogramSolver.buildLine(constraints, spacing, boardSize);

        assertArrayEquals(expected, result);
    }

    @Test
    void buildLine_withDoubleConstraint() {
        int boardSize = 5;
        CellValue[] expected = buildInput("11011");
        int[] constraints = new int[]{2, 2};
        int[] spacing = new int[]{0, 1, 0};

        CellValue[] result = NonogramSolver.buildLine(constraints, spacing, boardSize);

        assertArrayEquals(expected, result);
    }

    @Test
    void buildLine_withBadConstraints() {
        int boardSize = 5;

        int[] constraints = new int[]{6, 1};
        int[] spacing = new int[]{0, 1, 0};

        CellValue[] result = NonogramSolver.buildLine(constraints, spacing, boardSize);

        assertNull(result);
    }

    @Test
    void buildLine_withBadInnerSpacing() {
        int boardSize = 5;
        int[] constraints = new int[]{2, 2};
        int[] spacing = new int[]{0, 18, 0};

        CellValue[] result = NonogramSolver.buildLine(constraints, spacing, boardSize);

        assertNull(result);
    }

    @Test
    void isLineSolution_withValidInput() {
        int[] constraints = new int[]{2, 2};
        CellValue[] input = buildInput("11011");

        boolean result = NonogramSolver.isLineSolution(constraints, input);
        Assertions.assertTrue(result);
    }

    @Test
    void isLineSolution_withBadConstraints() {
        int[] constraints = new int[]{1, 1, 1};
        CellValue[] input = buildInput("11011");

        boolean result = NonogramSolver.isLineSolution(constraints, input);
        Assertions.assertFalse(result);
    }

    //HELPERS
    private CellValue[] buildInput(String input) {
        CellValue[] result = new CellValue[input.length()];

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                result[i] = CellValue.FILLED;
            } else {
                result[i] = CellValue.OPEN;
            }
        }

        return result;
    }

    private Cell[] getTestRow(int size) {
        Cell[] row = new Cell[size];
        for (int i = 0; i < row.length; i++) {
            row[i] = new Cell();
        }
        return row;
    }

    private Cell[] getAllConfirmedTestRow(int size) {
        Cell[] row = new Cell[size];
        for (int i = 0; i < row.length; i++) {
            row[i] = new Cell();
        }
        return row;
    }
}