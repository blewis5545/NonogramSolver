package com.butcher.nonogram;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
    void buildLine_withSingleConstraint(){
        int boardSize = 5;
        CellValue[] expected = new CellValue[]{
            CellValue.FILLED,
            CellValue.FILLED,
            CellValue.FILLED,
            CellValue.FILLED,
            CellValue.OPEN
        };
        int[] constraints = new int[]{4,0};

        CellValue[] result = NonogramSolver.buildLine(constraints,boardSize,1,0);

        assertArrayEquals(expected,result,"");
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