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

    public static CellValue[][] solveBoard(Board input) {
        //rows and their intersecting solutions will be linked by index
        int[][] rowConstraints = input.getRowConstraints();
        int[][] columnConstraints = input.getColumnConstraints();
        List<CellValue[]> rows = new ArrayList<>();
        List<CellValue[][][]> solutionsPerRow = new ArrayList<>();

        //find a guaranteed row to anchor the algorithm to


        //collect all possible column solutions for every row
        for (int i = 0; i < input.getRow(0).length; i++) {
            rows.add(input.getRow(i));
            solutionsPerRow.add(findIntersectingSolutions(rowConstraints[i], columnConstraints, i, input.getSize()));
        }


        //the possible column solutions for the first row must contain the solution for the whole board,
        // or else it is not solvable.

        int s = input.getSize();
        for (int a = 0; a < rows.size(); a++) {
            for (int b = 0; b < rows.size(); b++) {
                CellValue[][][] firstRowIntersectingSolutions = new CellValue[s][s][s];
                for (int c = 0; c < rows.size(); c++) {
                    CellValue[][] possibleSolutions = new CellValue[s][s];
                    for (int d = 0; d < rows.size(); d++) {

                    }
                }
            }
        }

        //at this point we should have intersecting solutions that are valid for the first row. This should filter out
        // most possible board solutions, and now we can repeat the process.
        // If we run out of rows before we hit one board solution, then the board has 2 possible solutions
    }

    //Each row will intersect every column, and vice versa. This means their constraints affect each other. Compare the
    // possible solutions of the target line with the ones that intersect it. This method should return solutions for
    // the targeted row that are valid against the intersecting rows.
    // <lineIndex> is the index on which all intersecting lines will cross over the target line
    // This method finds all possible solutions for each intersecting line when compared against one
    // perpendicular target line.
    public static CellValue[][][] findIntersectingSolutions(int[] targetLineConstraints, int[][] intersectingLineConstraints, int lineIndex, int boardSize) {
        CellValue[][] targetLineSolutions = findLineSolutions(targetLineConstraints, boardSize);

        List<CellValue[][]> intersectingLineSolutions = new ArrayList<>();
        for (int i = 0; i < intersectingLineConstraints.length; i++) {
            int[] currentConstraint = intersectingLineConstraints[i];
            intersectingLineSolutions.add(
                    findIntersectionSolutions(targetLineConstraints, currentConstraint, i, lineIndex, boardSize)
            );
        }

        return intersectingLineSolutions.toArray(new CellValue[0][0][0]);
    }

    //find possible solutions for two intersecting lines.
    // Return solutions for the first line that also satisfy the second.
    public static CellValue[][] findIntersectionSolutions(int[] firstLineConstraints, int[] secondLineConstraints, int firstIntersectIndex, int secondIntersectIndex, int boardSize) {
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
    public static CellValue[] getCrossSection(CellValue[][] lines, int index) {
        CellValue[] result = new CellValue[lines[0].length];

        for (int i = 0; i < lines.length; i++) {
            result[i] = lines[i][index];
        }

        return result;
    }

    //find a set of constraints that are valid for a given line
    public static int[] findLineConstraints(CellValue[] line) {
        List<Integer> constraints = new ArrayList<>();
        int currentChainLength = 0;
        int firstFilledIndex = -1;

        //finding the first filled index tells us a lot about the line, and if we don't find one
        // we can short circuit execution
        firstFilledIndex = Arrays.asList(line).indexOf(CellValue.FILLED);
        if (firstFilledIndex == -1) {
            return new int[]{0};
        }

        for (int i = firstFilledIndex; i < line.length; i++) {
            if (line[i] == CellValue.FILLED && (i < (line.length - 1))) {
                //finding filled squares just adds to the chain length until an empty square or
                // the last square in a line
                currentChainLength++;
            } else if (line[i] == CellValue.OPEN && (currentChainLength != 0)) {
                //close an existing chain
                constraints.add(currentChainLength);
                currentChainLength = 0;
            } else if (line[i] == CellValue.FILLED) {
                //close the last chain
                currentChainLength++;
                constraints.add(currentChainLength);
            }
        }

        return constraints
                .stream()
                .mapToInt(Integer::valueOf)
                .toArray();
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
            return multipleConstraintConfigurations(constraints, boardSize);
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

    public static CellValue[][] multipleConstraintConfigurations(int[] constraints, int boardSize) {
        List<CellValue[]> results = new ArrayList<>();
        //TODO: Create handling for multiple constraints
        return null;
    }

    public static boolean isLineSolution(int[] constraints, CellValue[] attempt) {
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

    //build the smallest possible line that still satisfies the given constraints
    public static CellValue[] buildMinimumLine(int[] constraints, int boardSize){
        List<CellValue> result = new ArrayList<>();
        if(minimumSpaceRequired(constraints) == boardSize){
            for(int i=0;i<constraints.length-1;i++){
                result.addAll(buildSequence(CellValue.FILLED,constraints[i]));
                result.add(CellValue.OPEN);
            }

            result.addAll(buildSequence(CellValue.FILLED, constraints[constraints.length-1]));

            return result.toArray(new CellValue[0]);
        }else{
            return null;
        }
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

    //try to find a guaranteed line with the given constraints
    public static CellValue[] lineHeuristics(int[][] constraints, int boardSize) {
        //if we have a line that is completely filled by the constraint, it is guaranteed
        int guaranteedIndex = -1;
        for (int i = 0; i < constraints.length; i++) {
            if (minimumSpaceRequired(constraints[i]) == boardSize) {
                guaranteedIndex = i;
                break;
            }
        }

        if(guaranteedIndex >= 0){
            int[] spacing = new int[]
            return buildLine(constraints[guaranteedIndex],)
        }

    }

    //return the minimum number of squares required to fulfill the given constraint
    public static int minimumSpaceRequired(int[] constraint) {
        int total = 0;
        for (int el : constraint) {
            total += el;
        }

        total += constraint.length - 1;

        return total;
    }

    //count instances of <value> in <array>
    public static int count(int[] array, int value) {
        int count = 0;
        for (int el : array) {
            if (el == value) {
                count++;
            }
        }

        return count;
    }

    public static boolean contains(int[] array, int value) {
        for (int el : array) {
            if (el == value) {
                return true;
            }
        }

        return false;
    }

    public static boolean allTrue(List<Boolean> list) {
        boolean oneValue = list.stream()
                .distinct()
                .count() == 1;
        return oneValue && list.get(0);
    }

    public static String rowToString(CellValue[] row) {
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
}
