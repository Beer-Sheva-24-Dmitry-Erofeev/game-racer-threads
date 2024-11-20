package telran.multithreading;

import java.util.Random;

public class Racer extends Thread {

    private final Race race;
    private final int number; // Номер гонщика

    Random sleepFor = new Random();

    public Racer(Race race, int number) {
        this.race = race;
        this.number = number;
    }

    // Running cycle containing number of iterations from the Race reference as the distance 
    // Each iteration is printing out the number of the thread for game tracing to see game dynamics
    @Override
    public void run() {
        for (int i = 0; i < race.getDistance(); i++) {
            System.out.println(number);
            try {
                sleep(sleepFor.nextInt(race.getMin_sleep_timeout(), race.getMax_sleep_time()));
            } catch (InterruptedException ex) {
                // Я спросил у Chat-GPT про это место, и он объяснил мне, что лучше делать так, 
                // а не оставлять блок catch пустым. Хотя в нашем случае это, видимо, не нужно.
                // Было бы здорово понять, как делать правильно и когда.
                Thread.currentThread().interrupt();
            }
        }
        // Для проверки какой из гонщиков завершил гонку первым
        System.out.printf("Racer %d finished!%n", number + 1);
        // Пытаемся записать номер победителя в переменную
        setWinnerNumber(number);
    }

    // Записываем номер победителя в переменную, если она уже не была изменена
    private void setWinnerNumber(int number) {
        Race.getWinnerNumberAtomic().compareAndSet(0, number + 1);
    }

}
