package com.butcher.nonogram;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.Arrays;

@SpringBootApplication
public class Nonogram {
    public static void main(String[] args) {
//        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.json")) {
//            NonogramSolver solver = new ObjectMapper().readValue(in, NonogramSolver.class);
//            solver.getBoard().setRow(3, test());
//            solver.solve();
//            solver.printBoard(System.out);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        try {
            Board b = new Board(5);

            b.setRow(0, NonogramSolver.buildLine(new int[]{3},new int[]{0,2}, 5));

        }
    }

    private static Cell[] test() {
        Cell[] row = new Cell[15];
        for (int i = 0; i < row.length; i++) {
            row[i] = new Cell();
        }
        row[3].setValue(CellValue.FILLED);

        return row;
    }
}
