package com.butcher.nonogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements IBoard {
    private int size;

    private Cell[][] cells;

    @Override
    public void setSize(int size) {
        this.size = size;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    @Override
    public CellValue[] getRow(int rowNum) {
        //hard copy to guarantee board integrity
        int numElements = cells[rowNum].length;
        CellValue[] row = new CellValue[numElements];

        for (int i = 0; i < numElements; i++) {
            row[i] = cells[rowNum][i].getValue();
        }

        return row;
    }

    //attempt to change a cell's value. return whether or not it was successful
    public boolean setCell(int x, int y, CellValue val) {
        //redundantly verbose for readability's sake
        if ((x >= 0) && (x < size)) {
            if ((y >= 0) && (y < size)) {
                cells[y][x].setValue(val);
                return true;
            }
        }
        return false;
    }

    /*
    @Override
    public List<Integer> setRow(int rowNum, Cell[] row) {
        List<Integer> changes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (row[i].isConfirmed()
                    && row[i].getValue() != CellValue.UNKNOWN
                    && cells[rowNum * size + i] != row[i]) {
                cells[rowNum * size + i] = row[i];
                changes.add(i);
            }
        }
        return changes;
    }*/
    public boolean setRow(int rowNum, CellValue[] values) {
        try {
            for (int i = 0; i < size; i++) {
                cells[rowNum][i].setValue(values[i]);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Cell[] getCol(int colNum) {
        //hard copy to guarantee board integrity
        int numElements = cells.length;
        Cell[] col = new Cell[numElements];

        for (int i = 0; i < numelements; i++) {
            col[i] = cells[i][colNum];
        }

        System.arraycopy(cells[rowNum], 0, row, 0, numElements);

        return row;
    }

    @Override
    public List<Integer> setCol(int colNum, Cell[] col) {
        List<Integer> changes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (col[i].isConfirmed() && col[i].getValue() != CellValue.UNKNOWN && cells[i * size + colNum] != col[i]) {
                cells[i * size + colNum] = col[i];
                changes.add(i);
            }
        }
        return changes;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                s.append(cells[r * size + c].getValue().value);
            }
            s.append("\r\n");
        }
        return s.toString();
    }
}
