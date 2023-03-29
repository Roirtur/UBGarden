package fr.ubx.poo.ugarden.launcher;

import fr.ubx.poo.ugarden.game.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

    public Game load() {
        Properties emptyConfig = new Properties();
        Configuration configuration = getConfiguration(emptyConfig);
        Position playerPosition = new Position(1,1,1);
        WorldLevels world = new WorldLevels(1);
        Game game = new Game(world, configuration, playerPosition);
        Map level = new Level(game, 1, new MapLevelDefaultStart());
        world.put(1, level);
        return game;
    }

}
