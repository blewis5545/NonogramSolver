package com.butcher.nonogram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    static CellValue[][] findLineSolutions(int[] constraints, int boardSize) {
        //we can't have more filled in squares than spaces on the board
        int numSquares = Arrays.stream(constraints).sum();
        if (numSquares > boardSize) {
            return null;
        }

        boolean hasInteriorBlanks = !contains(constraints, 0);
        if (!hasInteriorBlanks) {
            return singleConstraintConfigurations(constraints, boardSize);
        } else {
            return null; //TODO: add multiple constraint handler here
        }
    }

    //find valid configurations for a single row or column's constraints, given that there is only one constraint
    private static CellValue[][] singleConstraintConfigurations(int[] constraints, int boardSize) {
        List<CellValue[]> results = new ArrayList<>();
        int outerSpaces = boardSize - Arrays.stream(constraints).sum();
        if (outerSpaces < 0) {
            return null;
        }

        //shift the single run of filled squares from left to right in each iteration
        for (int i = 0; i <= outerSpaces; i++) {
            int[] spaceConstraints = new int[]{i, outerSpaces - i};

            results.add(buildLine(constraints, spaceConstraints, boardSize));
        }

        return results.toArray(new CellValue[0][0]);
    }

    static CellValue[][] multipleConstraintConfigurations(int[] constraints, int boardSize) {
        List<CellValue[]> results = new ArrayList<>();
        //TODO: Create handling for multiple constraints
        return null;
    }

    static boolean isLineSolution(int[] constraints, CellValue[] attempt) {
        String attemptString = rowToString(attempt);
        String patternString = "0*"; //build a regex string to check the row
        for (int i = 0; i < constraints.length - 1; i++) {
            patternString += "1{" + constraints[i] + "}";
            patternString += "0+";
        }
        patternString += "1{" + constraints[constraints.length - 1] + "}";
        patternString += "0*";

        return Pattern.matches(patternString, attemptString);
    }

    public static CellValue[] buildLine(int[] constraints, int[] spacing, int boardSize) {
        int totalSquares = Arrays.stream(constraints).sum() + Arrays.stream(spacing).sum();
        if ((totalSquares > boardSize) || (totalSquares < 0)) {
            return null;
        }

        //there should be a spacing value on each side of a constraint value. Even if it is 0.
        // They should "zip" together in the final row. So #constraints == #spacing - 1
        int difference = spacing.length - constraints.length;
        if (difference != 1) {
            return null;
        }

        //handle empty spaces to the left of the first filled in square
        List<CellValue> line = new ArrayList<>(buildSequence(CellValue.OPEN, spacing[0]));

        //handle the rest
        for (int i = 1; i < spacing.length; i++) {
            line.addAll(buildSequence(CellValue.FILLED, constraints[i - 1]));
            line.addAll(buildSequence(CellValue.OPEN, spacing[i]));
        }

        return line.toArray(new CellValue[0]);
    }

    //construct a repeating sequence of squares
    private static List<CellValue> buildSequence(CellValue value, int repetitions) {
        List<CellValue> results = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            results.add(value);
        }
        return results;
    }

    static int count(int[] array, int value) {
        int count = 0;
        for (int el : array) {
            if (el == value) {
                count++;
            }
        }
        return count;
    }

    static boolean contains(int[] array, int value) {
        for (int el : array) {
            if (el == value) {
                return true;
            }
        }
        return false;
    }

    static String rowToString(CellValue[] row) {
        String result = "";
        for (CellValue el : row) {
            if (el == CellValue.FILLED) {
                result += "1";
            } else {
                result += "0";
            }
        }
        return result;
    }

    void setSize(int size) {
        board = new Board();
        board.setSize(size);
    }

    void printBoard(PrintStream p) {
        p.println(board.toString());
    }
}
