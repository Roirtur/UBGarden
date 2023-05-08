package fr.ubx.poo.ugarden.launcher;

import fr.ubx.poo.ugarden.game.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class GameLauncher {
    final char EOL = 'x';

    // Default values
    private int playerLives = 5;
    private int playerInvincibilityDuration = 4;
    private int beeMoveFrequency = 1;
    private int playerEnergy = 100;
    private int energyBoost = 50;
    private int energyRecoverDuration = 5;
    private int diseaseDuration = 5;

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

    private Configuration getConfiguration() {

        System.out.println("Vies du joueur : " + playerLives);
        System.out.println("Temps d'invincibilité du joueur : " + playerInvincibilityDuration);
        System.out.println("Fréquence de mouvement de l'abeille : " + beeMoveFrequency);
        System.out.println("Energie max du joueur : " + playerEnergy);
        System.out.println("Gain d'energie par pomme : " + energyBoost);
        System.out.println("Temps pour récuperer 1 d'énergie : " + energyRecoverDuration);
        System.out.println("Temps de la maladie : " + diseaseDuration);


        return new Configuration(playerLives, playerEnergy, energyBoost, playerInvincibilityDuration, beeMoveFrequency, energyRecoverDuration, diseaseDuration);
    }

    public Game loadDefault(int default_choice) {
        MapLevel levelMap;
        if (default_choice == 1) {
            levelMap = new MapLevelDefaultStart();
        } else {
            levelMap = new MapLevelDefault();
        }
        Position playerPosition = levelMap.getPlayerPosition();
        if (playerPosition == null)
            throw new RuntimeException("Player not found");
        Configuration configuration = getConfiguration();
        WorldLevels world = new WorldLevels(1);

        ArrayList<Position>[] beePositions = new ArrayList[1];
        beePositions[0] = levelMap.getBeePositions(world.currentLevel());

        Game game = new Game(world, configuration, playerPosition, beePositions);
        Map level = new Level(1, levelMap);
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
                    case "playerLives":
                        playerLives = Integer.parseInt(valeur);
                        break;
                    case "playerInvincibilityDuration":
                        playerInvincibilityDuration = Integer.parseInt(valeur);
                        break;
                    case "beeMoveFrequency":
                        beeMoveFrequency = Integer.parseInt(valeur);
                        break;
                    case "playerEnergy":
                        playerEnergy = Integer.parseInt(valeur);
                        break;
                    case "energyBoost":
                        energyBoost = Integer.parseInt(valeur);
                        break;
                    case "energyRecoverDuration":
                        energyRecoverDuration = Integer.parseInt(valeur);
                        break;
                    case "diseaseDuration":
                        diseaseDuration = Integer.parseInt(valeur);
                        break;
                    default:
                        if (variable.startsWith("level")) {
                            String levelValue = variable.substring(5);
                            levels.add(valeur);
                            System.out.println("Niveau " + levelValue + " : " + valeur);
                            break;
                        } else {
                            System.out.println("Variable inconnue : " + variable);
                        }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
        Configuration configuration = getConfiguration();
        WorldLevels world = new WorldLevels(nblevels);

        ArrayList<Position>[] beePositions = new ArrayList[nblevels];
        for (int i = 0; i < nblevels; i++) {
            if (i != nblevels-1 && !levelMap.get(i).hasEndDoor()) {
                throw new RuntimeException("No door to next level on level " + (i+1));
            }

            beePositions[i] = levelMap.get(i).getBeePositions(i+1);
        }

        Game game = new Game(world, configuration, playerPosition, beePositions);
        for (int i = 0; i < nblevels-1; i++) {
            world.put(i+1, new Level(i+1, levelMap.get(i)));
        }


        if (!levelMap.get(nblevels-1).hasPrincess()) {
            if (!levelMap.get(nblevels-1).hasEndDoor()) {
                throw new RuntimeException("Couldn't find a way to win");
            }
            levelMap.get(nblevels-1).replaceDoorByPrincess();
        }
        if (levelMap.get(nblevels-1).hasEndDoor()) {
            throw new RuntimeException("Last level shouldn't have a door to a next level");
        }
        // Last Level
        world.put(nblevels, new Level(nblevels, levelMap.get(nblevels-1)));
        return game;
    }

}
