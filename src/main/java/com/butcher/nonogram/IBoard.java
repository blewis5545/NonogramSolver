package com.butcher.nonogram;

import java.util.List;

public interface IBoard {
    void setSize(int size);

    CellValue[] getRow(int rowNum);

    List<Integer> setRow(int rowNum, Cell[] row);

    boolean setCell(int x, int y, CellValue val);

    CellValue[] getCol(int colNum);

    boolean setCol(int colNum, Cell[] col);

    @Override
    String toString();
}
