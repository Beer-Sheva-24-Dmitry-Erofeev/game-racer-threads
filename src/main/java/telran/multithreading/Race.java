package telran.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Race {

    // "Дистанция" гонки + вариант по умолчанию
    private int distance;
    private final static int DEFAULT_DISTANCE = 20;

    // Количество участников гонки + вариант по умолчанию
    private int numberOfRacers;
    private final static int DEFAULT_NUMBER_OF_RACERS = 5;

    // Настройки таймаута для рандомайзера в мс
    private final int min_sleep_timeout = 2;
    private final int max_sleep_time = 5;

    // Массив "гонщиков"
    private Racer[] racers;

    // Номер победителя. Устанавливаем значение "0", чтобы с ним сравнивать
    private static AtomicInteger winnerNumber = new AtomicInteger(0);

    // Конструктор
    public Race(int distance, int numberOfRacers) {
        this.distance = distance;
        this.numberOfRacers = numberOfRacers;
        createRacers();
    }

    // Конструктор по умолчанию
    public Race() {
        this(DEFAULT_DISTANCE, DEFAULT_NUMBER_OF_RACERS);
    }

    // При изменении количества гонщиков нужно пересоздавать массив
    public final void createRacers() {
        Racer[] newRacers = new Racer[numberOfRacers];
        for (int i = 0; i < numberOfRacers; i++) {
            newRacers[i] = new Racer(this, i);
        }
        this.racers = newRacers; // обновляем поле racers
    }

    public Racer[] getRacers() {
        return racers;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getNumberOfRacers() {
        return numberOfRacers;
    }

    public void setNumberOfRacers(int numberOfRacers) {
        this.numberOfRacers = numberOfRacers;
    }

    public int getMin_sleep_timeout() {
        return min_sleep_timeout;
    }

    public int getMax_sleep_time() {
        return max_sleep_time;
    }

    public void clearWinner() {
        winnerNumber.set(0);
    }

    public int getWinnerNumber() {
        return winnerNumber.get();
    }

    public static AtomicInteger getWinnerNumberAtomic() {
        return winnerNumber;
    }

}
