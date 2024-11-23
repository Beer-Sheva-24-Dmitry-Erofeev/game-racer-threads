package telran.multithreading;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Race {

    // "Дистанция" гонки + вариант по умолчанию
    private int distance;
    private final static int DEFAULT_DISTANCE = 20;

    // Количество участников гонки + вариант по умолчанию
    private int numberOfRacers;
    private final static int DEFAULT_NUMBER_OF_RACERS = 5;

    // Настройки таймаута для рандомайзера в мс
    private final int minSleep = 2;
    private final int maxSleep = 5;

    // Массив "гонщиков"
    private Racer[] racers;

    // Отметка стартового времени
    public static LocalTime startTime;

    // Таблица с победителями. Подходит незащищённая, т.к. вся работа с ней только в блоке synchronized
    private final List<FinishRecord> finishTable = new ArrayList<>();

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

    public List<FinishRecord> getFinishTable() {
        return finishTable;
    }

    // synchronized - только один поток разом может тут работать (?)
    public synchronized void recordFinish(Racer racer) {
        LocalTime finishTime = LocalTime.now();
        Duration runningTime = Duration.between(startTime, finishTime);
        // Гонщики будут добавлены в порядке финиширования
        finishTable.add(new FinishRecord(finishTable.size() + 1, racer.getNumber(), runningTime));
    }

    // При изменении количества гонщиков нужно пересоздавать массив
    public final void createRacers() {
        Racer[] newRacers = new Racer[numberOfRacers];
        for (int i = 0; i < numberOfRacers; i++) {
            newRacers[i] = new Racer(this, i + 1);
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

    public int getMinSleep() {
        return minSleep;
    }

    public int getMaxSleep() {
        return maxSleep;
    }

    public static class FinishRecord {

        private final int place;
        private final int racerNumber;
        private final Duration runningTime;

        public FinishRecord(int place, int racerNumber, Duration runningTime) {
            this.place = place;
            this.racerNumber = racerNumber;
            this.runningTime = runningTime;
        }

        @Override
        public String toString() {
            return String.format("Place: %d | Racer: %d | Time: %d ms",
                    place, racerNumber, runningTime.toMillis());
        }
    }

}
