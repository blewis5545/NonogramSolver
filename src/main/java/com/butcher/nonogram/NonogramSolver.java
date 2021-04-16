package com.butcher.nonogram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.tools.javac.util.ArrayUtils;
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

    static CellValue[] solveLine(int[] constraints, CellValue[] line, int boardSize) {
        CellValue[] solution = line; //need array copy?

        int spacesNeeded = boardSize - Arrays.stream(constraints).sum();

        return solution;
    }

    //find potential solutions for a nonogram line
    static CellValue[][] findSolutions(int[] constraints, int boardSize) {
        //we can't have more filled in squares than spaces on the board
        int numSquares = Arrays.stream(constraints).sum();
        if (numSquares > boardSize) {
            return null;
        }

        int numOuterSpaces = boardSize - numSquares;

        boolean hasInteriorBlanks = !contains(constraints, 0);
        if (!hasInteriorBlanks) {
            return singleConstraintConfigurations(numSquares, numOuterSpaces);
        }else{
            
        }
    }

    //find valid configurations for a single row or column's constraints, given that there is only one constraint
    private static CellValue[][] singleConstraintConfigurations(int requiredSquares, int outerSpaces) {
        List<CellValue[]> results = new ArrayList<>();

        for (int i = 0; i < outerSpaces; i++) {
            List<CellValue> item = new ArrayList<>();

            //# blanks before squares
            for (int a = 0; a < i; a++) {
                item.add(CellValue.OPEN);
            }

            //# filled in squares
            for (int b = 0; b < requiredSquares; b++) {
                item.add(CellValue.FILLED);
            }

            //# blanks after squares
            int remainingSpaces = outerSpaces - i - requiredSquares;
            for (int c = 0; c < remainingSpaces; c++) {
                item.add(CellValue.OPEN);
            }

            results.add(item.toArray(new CellValue[0]));
        }

        return results.toArray(new CellValue[0][0]);
    }

    static boolean contains(int[] array, int value) {
        for (int el : array) {
            if (el == value) {
                return true;
            }
        }
        return false;
    }

    void setSize(int size) {
        board = new Board();
        board.setSize(size);
    }

    void printBoard(PrintStream p) {
        p.println(board.toString());
    }
}
