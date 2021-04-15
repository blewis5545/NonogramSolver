package com.butcher.nonogram;

public enum CellValue {
    FILLED("*"),
    OPEN("X"),
    UNKNOWN("U");

    public final String value;

    private CellValue(String value) {
        this.value = value;
    }
}
