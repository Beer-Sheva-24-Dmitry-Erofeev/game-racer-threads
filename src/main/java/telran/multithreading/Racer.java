package telran.multithreading;

import java.util.concurrent.ThreadLocalRandom;

public class Racer extends Thread {

    private final Race race;
    private final int number; // Номер гонщика

    public Racer(Race race, int number) {
        this.race = race;
        this.number = number;
    }

    @Override
    public void run() {

        int distance = race.getDistance();
        int sleepMin = race.getMinSleep();
        int sleepMax = race.getMaxSleep();

        for (int i = 0; i < distance; i++) {
            System.out.println(number);
            try {
                sleep(ThreadLocalRandom.current().nextInt(sleepMin, sleepMax));
            } catch (InterruptedException ex) {
            }
        }
        race.recordFinish(this);
    }

    public int getNumber() {
        return number;
    }

}
