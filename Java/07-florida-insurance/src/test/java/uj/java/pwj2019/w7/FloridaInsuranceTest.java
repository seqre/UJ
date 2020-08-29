package uj.java.pwj2019.w7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FloridaInsuranceTest {

    final static String EXPECTED = "expected/";
    final static String COUNT = "count.txt";
    final static String MOST_VALUABLE = "most_valuable.txt";
    final static String TIV_2012 = "tiv2012.txt";

    @BeforeAll
    public static void runMainApp() {
        FloridaInsurance.main(new String[0]);
    }

    @Test
    public void checkCount() {
        BigDecimal expectedCount = new BigDecimal(readFile(EXPECTED + COUNT));
        System.out.println(expectedCount);
        BigDecimal actualCount = new BigDecimal(readFile(COUNT));
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void checkTiv2012() {
        BigDecimal expectedTiv = new BigDecimal(readFile(EXPECTED + TIV_2012));
        System.out.println(expectedTiv);
        BigDecimal actualTiv = new BigDecimal(readFile(TIV_2012));
        assertEquals(expectedTiv, actualTiv);
    }

    @Test
    public void checkMostValuable() {
        String expectedMV = readFile(EXPECTED + MOST_VALUABLE);
        System.out.println(expectedMV);
        String actualMV = readFile(MOST_VALUABLE);
        assertEquals(expectedMV, actualMV);
    }

    private static String readFile(String fileName) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            result = br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
