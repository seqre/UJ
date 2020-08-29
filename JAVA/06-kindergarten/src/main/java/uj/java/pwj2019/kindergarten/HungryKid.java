package uj.java.pwj2019.kindergarten;

import java.util.concurrent.Semaphore;

public class HungryKid extends Child {
    private final Semaphore firstFork;
    private final Semaphore secondFork;
    private final Thread innerThread;

    public HungryKid(String name, int hungerSpeedMs, Semaphore firstFork, Semaphore secondFork) {
        super(name, hungerSpeedMs);
        this.firstFork = firstFork;
        this.secondFork = secondFork;
        this.innerThread = new Thread(this::consume);
        innerThread.start();
    }

    public void consume() {
        try {
            while (true) {
                firstFork.acquire();
                secondFork.acquire();
                super.eat();
                secondFork.release();
                firstFork.release();
                Thread.sleep(this.hungerSpeed());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
