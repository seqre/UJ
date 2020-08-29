package uj.pwj2019.w9;

public class Librarian {

    public static void main(String[] args) {
        int exitCode = 0;
        exitCode = returnsZeroForIdenticalData(exitCode);
        exitCode = returnsMinusOneForFirstFieldDifferent(exitCode);
        exitCode = returnsMinusOneForNotAnnotatedFieldDifferent(exitCode);
        exitCode = prefersComparePriorityOverOrdering(exitCode);
        System.exit(exitCode);
    }

    private static int returnsZeroForIdenticalData(int exitCode) {
        System.out.print("returnsZeroForIdenticalData... ");
        var data1 = new SecretData(5, true, 'a', 1.0f, "secret");
        var data2 = new SecretData(5, true, 'a', 1.0f, "don't tell anyone!");

        var comparator = new SecretDataComparator();
        if (comparator.compare(data1, data2) != 0 ) {
            System.out.println("FAIL");
            return 1;
        }
        System.out.println("PASS");
        return exitCode;
    }

    private static int returnsMinusOneForFirstFieldDifferent(int exitCode) {
        System.out.print("returnsMinusOneForFirstFieldDifferent... ");
        var data1 = new SecretData(5, true, 'a', 1.0f, "secret");
        var data2 = new SecretData(10, true, 'a', 1.0f, "don't tell anyone!");

        var comparator = new SecretDataComparator();
        if (comparator.compare(data1, data2) >= 0 ) {
            System.out.println("FAIL");
            return 1;
        }
        System.out.println("PASS");
        return exitCode;
    }

    private static int returnsMinusOneForNotAnnotatedFieldDifferent(int exitCode) {
        System.out.print("returnsMinusOneForNotAnnotatedFieldDifferent... ");
        var data1 = new SecretData(5, true, 'a', 1.0f, "secret");
        var data2 = new SecretData(5, true, 'a', 2.0f, "don't tell anyone!");

        var comparator = new SecretDataComparator();
        if (comparator.compare(data1, data2) >= 0 ) {
            System.out.println("FAIL");
            return 1;
        }
        System.out.println("PASS");
        return exitCode;
    }


    private static int prefersComparePriorityOverOrdering(int exitCode) {
        System.out.print("prefersComparePriorityOverOrdering... ");
        var data1 = new SecretData(5, true, 'a', 1.0f, "secret");
        var data2 = new SecretData(5, false, 'b', 1.0f, "don't tell anyone!");

        var comparator = new SecretDataComparator();
        if (comparator.compare(data1, data2) >= 0 ) {
            System.out.println("FAIL");
            return 1;
        }
        System.out.println("PASS");
        return exitCode;
    }

}
