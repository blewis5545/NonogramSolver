package com.butcher.nonogram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;
import java.util.*;

/*
@Getter
@Setter
class NonogramSolver {
    @JsonIgnore
    private Board board;
    private int size;
    private int[][] rows;
    private int[][] cols;

    void solve() {
        Set<Integer> rowChanges = new HashSet<>();
        Set<Integer> colChanges = new HashSet<>();
        for (int r = 0; r < size; r++) {
            List<Integer> changes = board.setRow(
                    r, solveLine(rows[r], board.getRow(r))
            );
            rowChanges.addAll(changes);
        }
        for (int c = 0; c < size; c++) {
            List<Integer> changes = board.setCol(
                    c, solveLine(cols[c], board.getCol(c))
            );
            colChanges.addAll(changes);
        }

        while (!rowChanges.isEmpty() || !colChanges.isEmpty()) {
            for (int r = 0; r < colChanges.size(); r++) {
                List<Integer> changes = board.setRow(
                        r, solveLine(rows[r], board.getRow(r))
                );
                rowChanges.addAll(changes);
            }
            for (int c = 0; c < rowChanges.size(); c++) {
                List<Integer> changes = board.setCol(
                        c, solveLine(cols[c], board.getCol(c))
                );
                colChanges.addAll(changes);
            }
        }
    }

    //can only mark confirmed unknowns unless it makes a pass at the end.
    static Cell[] solveLine(int[] values, Cell[] row) {
        Cell[] solution = row; //need array copy?

        int spacesNeeded = Arrays.stream(values).sum() + values.length;
        int startingIdx = 0;
        while (startingIdx < spacesNeeded-1) {
            Cell[] working = solution; //need array copy?
            for (int cell = 0; cell < row.length; cell++) {
                //for ()
            }
            startingIdx++;
        }
        return solution;
    }

    static Cell[] lineHeuristics(int[] values, Cell[] row){
        Cell[] result =
        for (Cell c : row){

        }


    }

    void setSize(int size) {
        board = new Board();
        board.setSize(size);
    }

    void printBoard(PrintStream p) {
        p.println(board.toString());
    }
}*/

@Getter
@Setter
class NonogramSolver {
    @JsonIgnore
    private Board board;
    private int size;

}
