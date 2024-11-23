package telran.multithreading;

import java.time.LocalTime;
import java.util.Arrays;

import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class Main {

    // Создаём гонку с дефолтными настройками
    private static final Race race = new Race();

    // Cоздаём дефолтное количетсво гонщиков
    private static final Racer[] racersDefault = new Racer[race.getNumberOfRacers()];

    public static void main(String[] args) {
        // Создаём пункты меню
        Item[] items = GameMenuItems.getItems(race);
        // Создаём и запускаем меню
        Menu menu = new Menu("***** Thread-Racing-Game *****", items);
        menu.perform(new StandardInputOutput());
        // Гонка запускается из меню
    }

    public static void performRace(Racer[] racers) {
        Race.startTime = LocalTime.now();
        startRace(racers);
        completeRace(racers);
        printResults();
        race.getFinishTable().clear();
    }

    // Запускаем гонку с параметрами по умолчанию
    public static void performRace() {
        performRace(racersDefault);
    }

    // Запуск гонки - просто стартуем все потоки.
    private static void startRace(Racer[] racers) {
        for (int i = 0; i < racers.length; i++) {
            racers[i] = new Racer(race, i);
            racers[i].start();
        }

    }

    // Проводим и завершаем гонку
    private static void completeRace(Racer[] racers) {
        Arrays.stream(racers).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        });
    }

    // Выводим таблицу результатов
    private static void printResults() {
        System.out.println("\nFinish Table:");
        race.getFinishTable().forEach(System.out::println);
        System.out.println();
    }
}
