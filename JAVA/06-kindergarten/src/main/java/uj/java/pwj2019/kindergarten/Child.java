package uj.java.pwj2019.kindergarten;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Child {

    private final Thread innerChild;
    private final String name;
    private final int hungerSpeedMs;
    private AtomicInteger happiness = new AtomicInteger(100);

    public Child(String name, int hungerSpeedMs) {
        this.name = name;
        this.hungerSpeedMs = hungerSpeedMs;
        innerChild = new Thread(this::hungerLoop, "[InnerChild " + name + "]");
        innerChild.start();
        System.out.println("Child " + name + " came for dinner.");
    }

    private void hungerLoop () {
        while (true) {
            try {
                Thread.sleep(hungerSpeedMs);
                int currentLevel = happiness.decrementAndGet();
                if (currentLevel <= 0) {
                    System.err.println("Child " + name + " is crying!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected final void eat() {
        try {
            final String tName = Thread.currentThread().getName();
            System.out.println("["+ tName + "] Child " + name + " is eating, happiness: " + happiness.get());
            Thread.sleep(100);
            happiness.set(100);
            System.out.println("["+ tName + "] Child " + name + " finished eating.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public final int hungerSpeed() {
        return hungerSpeedMs;
    }

    public final int happiness() {
        return happiness.get();
    }

    public final String name() {
        return name;
    }

}
