package uj.java.pwj2019.w02;

import java.math.BigInteger;

public class Spreadsheet {

    private String[][] data;

    public String[][] calculate(String[][] input) {
        data = input;

        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[0].length; column++) {
                data[row][column] = parseCell(row, column);
            }
        }

        return data;
    }

    private String parseCell(int row, int column) {
        switch (data[row][column].charAt(0)) {
            case '$':
                return parseCell(getRowFromReference(data[row][column]), getColumnFromReference(data[row][column]));

            case '=':
                return parseOperations(row, column);

            default:
                return data[row][column];
        }
    }

    private BigInteger referenceToBigInteger(String operand) {
        if (Character.isDigit(operand.charAt(0))) {
            return new BigInteger(operand);
        } else {
            return new BigInteger(parseCell(getRowFromReference(operand), getColumnFromReference(operand)));
        }
    }

    private String parseOperations(int row, int column) {
        String[] operationOperands = data[row][column].substring(4)
                .replaceAll("[()]", "")
                .split(",");

        BigInteger firstOperand = referenceToBigInteger(operationOperands[0]);
        BigInteger secondOperand = referenceToBigInteger(operationOperands[1]);

        switch (data[row][column].substring(1, 4)) {
            case "ADD":
                return firstOperand.add(secondOperand).toString();

            case "SUB":
                return firstOperand.subtract(secondOperand).toString();

            case "MUL":
                return firstOperand.multiply(secondOperand).toString();

            case "DIV":
                return firstOperand.divide(secondOperand).toString();

            case "MOD":
                return firstOperand.mod(secondOperand).toString();
        }
        return null;
    }

    private int getRowFromReference(String operand) {
        return Integer.parseInt(operand.substring(2)) - 1;
    }

    private int getColumnFromReference(String operand) {
        return (int) operand.charAt(1) - (int) 'A';
    }

}
