package uj.java.pwj2019.kindergarten;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Kindergarten {
    static List<HungryKid> bubbles;
    static List<Semaphore> forks;

    public static void main(String[] args) throws IOException {
        init();

        final var fileName = args[0];
        System.out.println("File name: " + fileName);

        try (var in = new Scanner(new FileReader(new File(fileName)))) {
            int numberOfKids = in.nextInt();

            bubbles = new ArrayList<>(numberOfKids);
            forks = new ArrayList<>(numberOfKids);

            IntStream.range(0, numberOfKids).forEach((i) -> forks.add(new Semaphore(1)));
            IntStream.range(0, numberOfKids).forEach((i) -> {
                if (i % 3 == 0) {
                    bubbles.add(new HungryKid(in.next(), in.nextInt(), forks.get(i), forks.get((i + 1) % forks.size())));
                } else {
                    bubbles.add(new HungryKid(in.next(), in.nextInt(), forks.get((i + 1) % forks.size()), forks.get(i)));
                }
            });
        }
    }

    private static void init() throws IOException {
        Files.deleteIfExists(Path.of("out.txt"));
        System.setErr(new PrintStream(new FileOutputStream("out.txt")));
        new Thread(Kindergarten::runKindergarden).start();
    }

    private static void runKindergarden() {
        try {
            Thread.sleep(10100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<String> errLines = Files.readAllLines(Path.of("out.txt"));
            System.out.println("Children cries count: " + errLines.size());
            errLines.forEach(System.out::println);
            System.exit(errLines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
