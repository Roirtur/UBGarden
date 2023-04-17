package fr.ubx.poo.ugarden.launcher;

import fr.ubx.poo.ugarden.game.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Properties;

public class GameLauncher {

    private static class LoadSingleton {
        static final GameLauncher INSTANCE = new GameLauncher();
    }
    private GameLauncher() {}

    public static GameLauncher getInstance() {
        return LoadSingleton.INSTANCE;
    }

    private int integerProperty(Properties properties, String name, int defaultValue) {
        return Integer.parseInt(properties.getProperty(name, Integer.toString(defaultValue)));
    }

    private boolean booleanProperty(Properties properties, String name, boolean defaultValue) {
        return Boolean.parseBoolean(properties.getProperty(name, Boolean.toString(defaultValue)));
    }

    private Configuration getConfiguration(Properties properties) {

        // Load parameters
        int playerLives = integerProperty(properties, "playerLives", 5);
        int playerInvincibilityDuration = integerProperty(properties, "playerInvincibilityDuration", 4);
        int beeMoveFrequency = integerProperty(properties, "beeMoveFrequency", 1);
        int playerEnergy = integerProperty(properties, "playerEnergy", 100);
        int energyBoost = integerProperty(properties, "energyBoost", 50);
        int energyRecoverDuration = integerProperty(properties, "energyRecoverDuration", 5);
        int diseaseDuration = integerProperty(properties, "diseaseDuration", 5);

        return new Configuration(playerLives, playerEnergy, energyBoost, playerInvincibilityDuration, beeMoveFrequency, energyRecoverDuration, diseaseDuration);
    }

    public Game loadDefault(int default_choice) {
        Properties emptyConfig = new Properties();
        MapLevel levelMap;
        if (default_choice == 1) {
            levelMap = new MapLevelDefaultStart();
        } else {
            levelMap = new MapLevelDefault();
        }
        Position playerPosition = levelMap.getPlayerPosition();
        if (playerPosition == null)
            throw new RuntimeException("Player not found");
        Configuration configuration = getConfiguration(emptyConfig);
        WorldLevels world = new WorldLevels(1);

        ArrayList<Position> beePositions = levelMap.getBeePositions(world.currentLevel());
        Game game = new Game(world, configuration, playerPosition, beePositions);
        Map level = new Level(game, 1, levelMap);
        world.put(1, level);
        return game;
    }

}
