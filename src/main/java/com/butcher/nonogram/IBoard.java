package com.butcher.nonogram;

import java.util.List;

public interface IBoard {
    int getSize();

    CellValue getCell(int x, int y);

    boolean setCell(int x, int y, CellValue val);

    CellValue[] getRow(int rowNum);

    boolean setRow(int rowNum, CellValue[] row);

    CellValue[] getCol(int colNum);

    boolean setCol(int colNum, CellValue[] col);

    @Override
    String toString();
}
