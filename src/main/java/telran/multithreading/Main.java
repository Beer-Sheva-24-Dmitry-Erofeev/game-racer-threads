package telran.multithreading;

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

    // Запускаем гонку
    public static void performRace(Racer[] racers) {
        // Стартуем гонку
        startRace(racers);
        // Завершаем гонку
        completeRace(racers);
        // Выводим строку с победителем
        printRaceWinnerNumber();
        // Очищаем переменную с победителем
        race.clearWinner();
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
                // Я спросил у Chat-GPT про это место, и он объяснил мне, что лучше делать так, 
                // а не оставлять блок catch пустым. Хотя в нашем случае это, видимо, не нужно.
                // Было бы здорово понять, как делать правильно и когда.
                Thread.currentThread().interrupt();
            }
        });
    }

    // Выводим в консоль номер победителя
    private static void printRaceWinnerNumber() {
        System.out.printf("\nWe had %d racers and the race was %d kilometers long!\n", race.getNumberOfRacers(), race.getDistance());
        System.out.printf("And our winner is... racer number %d!\n\n", race.getWinnerNumber());
    }
}
