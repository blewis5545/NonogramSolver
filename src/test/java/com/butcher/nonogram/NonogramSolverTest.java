package com.butcher.nonogram;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void solveLine() {
        Cell[] expected = getAllConfirmedTestRow(5);
        expected[0].setValue(CellValue.FILLED);
        expected[1].setValue(CellValue.OPEN);
        expected[2].setValue(CellValue.FILLED);
        expected[3].setValue(CellValue.FILLED);
        expected[4].setValue(CellValue.FILLED);

        int[] input = {1, 3};

//        Cell[] result = NonogramSolver.solveLine(input, getTestRow(5));

        //assertArrayEquals(expected, result);

    }

    @Test
    void buildLine_withSingleConstraint() {
        int boardSize = 5;
        CellValue[] expected = new CellValue[]{
                CellValue.FILLED,
                CellValue.FILLED,
                CellValue.FILLED,
                CellValue.FILLED,
                CellValue.OPEN
        };
        int[] constraints = new int[]{4, 0};

        CellValue[] result = NonogramSolver.buildLine(constraints, boardSize, 0, 0);

        assertArrayEquals(expected, result);
    }

    @Test
    void buildLine_withDoubleConstraint() {
        int boardSize = 5;
        CellValue[] expected = new CellValue[]{
                CellValue.FILLED,
                CellValue.FILLED,
                CellValue.OPEN,
                CellValue.FILLED,
                CellValue.FILLED
        };
        int[] constraints = new int[]{2, 2};

        CellValue[] result = NonogramSolver.buildLine(constraints, boardSize, 0, 1);

        assertArrayEquals(expected, result);
    }

    @Test
    void buildLine_withBadConstraints() {
        int boardSize = 5;

        int[] constraints = new int[]{6, 0};

        CellValue[] result = NonogramSolver.buildLine(constraints, boardSize, 0, 0);

        assertEquals(null, result);
    }

    @Test
    void buildLine_withBadInnerSpacing() {
        int boardSize = 5;
        int[] constraints = new int[]{2, 2};

        CellValue[] result = NonogramSolver.buildLine(constraints, boardSize, 0, 3);

        assertEquals(null, result);
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