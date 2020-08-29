package uj.java.pwj2019.w7;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FloridaInsurance {
    private static ZipFile zipFile;

    public static void main(String[] args) {
        try (ZipFile data = new ZipFile(new File("FL_insurance.csv.zip"))) {
            zipFile = data;

            count();
            tiv2012();
            mostValuable();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void count() {
        try {
            long count = Objects.requireNonNull(getData())
                    .stream()
                    .map(InsuranceEntry::country)
                    .distinct()
                    .count();

            BufferedWriter writer = getBufferedWriter("count.txt");
            writer.write(Long.valueOf(count).toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tiv2012() {
        try {
            BigDecimal sum = Objects.requireNonNull(getData())
                    .stream()
                    .map(InsuranceEntry::tiv2012)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BufferedWriter writer = getBufferedWriter("tiv2012.txt");
            writer.write(sum.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mostValuable() {
        try {
            BufferedWriter writer = getBufferedWriter("most_valuable.txt");
            writeTo(writer, "country,value");

            Objects.requireNonNull(getData())
                    .stream()
                    .map((entry) -> new Pair(entry.country(), entry.tiv2012().subtract(entry.tiv2011())))
                    .collect(Collectors.groupingBy(Pair::getFirst, Collectors.reducing(BigDecimal.ZERO, Pair::getSecond, BigDecimal::add)))
                    .entrySet()
                    .stream()
                    .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                    .limit(10)
                    .forEach(entry -> writeTo(writer, entry.getKey() + "," + entry.getValue().toPlainString()));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedWriter getBufferedWriter(String name) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name)));
    }

    private static void writeTo(BufferedWriter writer, String str) {
        try {
            writer.write(str);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<InsuranceEntry> getData() throws IOException {
        ZipEntry zipEntry = zipFile.getEntry("FL_insurance.csv");

        return (new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry))))
                .lines()
                .skip(1)
                .map((entry) -> getEntry(entry.split(",")))
                .collect(Collectors.toList());
    }

    private static InsuranceEntry getEntry(String[] arg) {
        /*
            0 - policyID,
            1 - statecode,
            2 - county,
            3 - eq_site_limit,
            4 - hu_site_limit,
            5 - fl_site_limit,
            6 - fr_site_limit,
            7 - tiv_2011,
            8 - tiv_2012,
            9 - eq_site_deductible,
            10 - hu_site_deductible,
            11 - fl_site_deductible,
            12 - fr_site_deductible,
            13 - point_latitude,
            14 - point_longitude,
            15 - line,
            16 - construction,
            17 - point_granularity
         */
        return new InsuranceEntry(
                Integer.parseInt(arg[0]),
                arg[2],
                BigDecimal.valueOf(Double.parseDouble(arg[7])),
                BigDecimal.valueOf(Double.parseDouble(arg[8])),
                Line.valueOf(arg[15].toUpperCase()),
                arg[16],
                Double.parseDouble(arg[13]),
                Double.parseDouble(arg[14])
        );
    }

    private static class Pair {
        private String first;
        private BigDecimal second;

        public Pair(String first, BigDecimal second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        public BigDecimal getSecond() {
            return second;
        }
    }
}


