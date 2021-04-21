package com.butcher.nonogram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Pattern;

@Getter
@Setter
class NonogramSolver {

    //Each row will intersect every column, and vice versa. This means their constraints affect each other. Compare the
    // possible solutions of the target line with the ones that intersect it. This method should return solutions for
    // the targeted row that are valid against the intersecting rows.
    // <lineIndex> is the index on which all intersecting lines will cross over the target line
    static CellValue[][] solveLine(int[] targetLineConstraints, int[][] intersectingLineConstraints, int lineIndex, int boardSize) {
        CellValue[][] targetLineSolutions = findLineSolutions(targetLineConstraints, boardSize);

        List<CellValue[][]> intersectingLineSolutions = new ArrayList<>();
        for (int i = 0; i < intersectingLineConstraints.length; i++) {
            int[] currentConstraint = intersectingLineConstraints[i];
            intersectingLineSolutions.add(findIntersectionSolutions(targetLineConstraints, currentConstraint, i, lineIndex, boardSize));
        }

        //sift through the available solutions and find ones that satisfy both the target line and all other intersecting lines
        for(CellValue[][] solutions : intersectingLineSolutions){
            for(int i=0;i<targetLineSolutions.length;i++){
                for(int j=0;j<solutions.length;j++){

                }
            }
        }
    }

    //find possible solutions for two intersecting lines.
    // Return solutions for the first line that also satisfy the second.
    static CellValue[][] findIntersectionSolutions(int[] firstLineConstraints, int[] secondLineConstraints, int firstIntersectIndex, int secondIntersectIndex, int boardSize) {
        //solve both lines individually first
        CellValue[][] firstSolutions = findLineSolutions(firstLineConstraints, boardSize);
        CellValue[][] secondSolutions = findLineSolutions(secondLineConstraints, boardSize);

        //look for solutions that have the same value at the intersection point
        List<CellValue[]> solutions = new ArrayList<>();
        for (int i = 0; i < firstSolutions.length; i++) {
            for (int j = 0; j < secondSolutions.length; j++) {
                if (firstSolutions[i][firstIntersectIndex] == secondSolutions[j][secondIntersectIndex]) {
                    solutions.add(firstSolutions[i]);
                }
            }
        }

        return solutions.toArray(new CellValue[0][0]);
    }

    //collect the n'th index of each line
    static CellValue[] getCrossSection(CellValue[][] lines, int index){
        CellValue[] result = new CellValue[lines[0].length];

        for(int i=0;i<lines.length;i++){
            result[i] = lines[i][index];
        }

        return result;
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
