package fr.ubx.poo.ugarden.launcher;

import fr.ubx.poo.ugarden.game.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class GameLauncher {
    final char EOL = 'x';

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

        ArrayList<Position>[] beePositions = new ArrayList[1];
        beePositions[0] = levelMap.getBeePositions(world.currentLevel());

        Game game = new Game(world, configuration, playerPosition, beePositions);
        Map level = new Level(game, 1, levelMap);
        world.put(1, level);
        return game;
    }

    public Game loadFile(File file) {
        boolean compression = false;
        int nblevels = 0;
        ArrayList<String> levels = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (!ligne.contains("=")) {
                    continue; // ignore la ligne qui ne contient pas "="
                }

                String[] variableValeur = ligne.split(" = ");
                variableValeur.toString();
                String variable = variableValeur[0];
                String valeur = variableValeur[1];
                switch(variable) {
                    case "compression":
                        compression = Boolean.valueOf(valeur);
                        System.out.println("Compression : " + compression);
                        break;
                    case "levels":
                        nblevels = Integer.parseInt(valeur);
                        System.out.println("Nombre de niveaux : " + levels);
                        break;
                    case "level1":
                        levels.add(valeur);
                        System.out.println("Niveau 1 : " + valeur);
                        break;
                    case "level2":
                        levels.add(valeur);
                        System.out.println("Niveau 2 : " + valeur);
                        break;
                    case "playerLives":
                        int playerLives = Integer.parseInt(valeur);
                        System.out.println("Vies du joueur : " + playerLives);
                        break;
                    case "beeMoveFrequency":
                        int beeMoveFrequency = Integer.parseInt(valeur);
                        System.out.println("Fréquence de mouvement de l'abeille : " + beeMoveFrequency);
                        break;
                    default:
                        System.out.println("Variable inconnue : " + variable);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Properties emptyConfig = new Properties();
        ArrayList<MapLevel> levelMap = new ArrayList<MapLevel>();

        for (String level : levels) {
            String string = level;
            String[] lines = string.split(String.valueOf(EOL));
            if (!compression) {
                int width = lines[0].length();
                int height = lines.length;
                levelMap.add(new MapLevelString(lines, width, height));
            } else {
                int width = 0;
                for (int i = 0; i < lines[0].length(); i++) {
                    if (Character.isDigit(lines[0].charAt(i))) {
                        width += Character.getNumericValue(lines[0].charAt(i)) - 1;
                    }
                    else width++;
                }
                int height = lines.length;
                levelMap.add(new MapLevelStringRLE(lines, width, height));
            }

        }


        Position playerPosition = levelMap.get(0).getPlayerPosition();
        if (playerPosition == null)
            throw new RuntimeException("Player not found");
        Configuration configuration = getConfiguration(emptyConfig);
        WorldLevels world = new WorldLevels(nblevels);

        ArrayList<Position>[] beePositions = new ArrayList[nblevels];
        for (int i = 0; i < nblevels; i++) {
            beePositions[i] = levelMap.get(i).getBeePositions(i+1);
        }

        Game game = new Game(world, configuration, playerPosition, beePositions);
        for (int i = 0; i < nblevels; i++) {
            world.put(i+1, new Level(game, i+1, levelMap.get(i)));
        }
        return game;
    }

}
