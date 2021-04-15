package com.butcher.nonogram;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements IBoard {
    private Cell[][] cells;

    @Getter
    private int size;

    @Getter
    @Setter
    private int[][] rowConstraints; //consists of 2 numbers for each row. e.g. [2,1] means 2 blocks, then a space, then 1 filled in

    @Getter
    @Setter
    private int[][] columnConstraints; //same as rowConstraints

    public void setSize(int size) {
        this.size = size;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public CellValue getCell(int x, int y){
        if ((x >= 0) && (x < size)) {
            if ((y >= 0) && (y < size)) {
                return cells[y][x].getValue();
            }
        }
        return null;
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

    public CellValue[] getRow(int rowNum) {
        int numElements = cells[rowNum].length;
        CellValue[] row = new CellValue[numElements];

        for (int i = 0; i < numElements; i++) {
            row[i] = getCell(i,rowNum); //rowNum == y coordinate
        }

        return row;
    }

    //sets the values of an entire row
    public boolean setRow(int rowNum, CellValue[] values) {
        List<Boolean> results = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            results.add(setCell(i, rowNum, values[i])); //rowNum == y coordinate
        }

        return results.stream()
            .distinct()
            .count() == 1;
    }

    public CellValue[] getCol(int colNum) {
        int numElements = cells.length;
        CellValue[] column = new CellValue[numElements];

        for (int i=0;i<numElements;i++){
            column[i] = getCell(colNum,i); // colNum == x coordinate
        }

        return column;
    }

    public boolean setCol(int colNum, CellValue[] values) {
        List<Boolean> results = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            results.add(setCell(colNum, i, values[i])); //rowNum == y coordinate
        }

        return results.stream()
                .distinct()
                .count() == 1;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                s.append(getCell(x,y).value);
            }
            s.append("\r\n");
        }
        return s.toString();
    }
}
