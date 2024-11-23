package telran.multithreading;

import telran.view.InputOutput;
import telran.view.Item;

public class GameMenuItems {

    private static Race race;

    public static Item[] getItems(Race race) {

        GameMenuItems.race = race;

        Item[] items = {
            Item.of("Start the race!", GameMenuItems::startRace),
            Item.of("Set the distance of the race.", GameMenuItems::enterDistance),
            Item.of("Set tne number of racers.", GameMenuItems::enterNumberOfRacers),
            Item.ofExit()
        };
        return items;
    }

    private static void enterDistance(InputOutput io) {
        int distance = io.readInt("Type in the distance of the race in kilometers", "Wrong data");
        race.setDistance(distance);
        System.out.printf("The distance is set to: %d kilometers\n\n", race.getDistance());
    }

    private static void enterNumberOfRacers(InputOutput io) {
        int nRacers = io.readInt("Type in the number of competing racers", "Wrong data");
        race.setNumberOfRacers(nRacers);
        race.createRacers();
        System.out.printf("The number of racers is set to: %d\n\n", race.getNumberOfRacers());
    }

    private static void startRace(InputOutput io) {
        Main.performRace(race.getRacers());
    }
}
