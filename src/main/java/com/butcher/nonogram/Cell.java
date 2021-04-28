package com.butcher.nonogram;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    private CellValue value;
//    private boolean confirmed;

    public Cell() {
        value = CellValue.OPEN;
    }

    @Override
    public String toString() {
        return value.value;
    }
}
