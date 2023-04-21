package fr.ubx.poo.ugarden.game;

import fr.ubx.poo.ugarden.go.decor.Door;
import fr.ubx.poo.ugarden.go.personage.Bee;
import fr.ubx.poo.ugarden.go.personage.Player;

import java.util.ArrayList;
import java.util.Collections;


public class Game {

    private final Configuration configuration;

    public Configuration configuration() {
        return configuration;
    }

    private final World world;
    private final Player player;

    private boolean switchLevelRequested = false;
    private int switchLevel;
    private final ArrayList<Bee> allBees = new ArrayList<Bee>();
    private final ArrayList<ArrayList<Position>> beesPositions = new ArrayList<>();

    public Game(World world, Configuration configuration, Position playerPosition, ArrayList<Position>[] beePositions) {
        this.configuration = configuration;
        this.world = world;
        player = new Player(this, playerPosition);

        Collections.addAll(this.beesPositions, beePositions);
        loadBees();
    }

    public ArrayList<Bee> getBees() {return this.allBees;}

    public void loadBees() {
        for (Position position : beesPositions.get(world.currentLevel()-1)) {
            this.allBees.add(new Bee(this, position));
        }
    }

    public void deleteBee(ArrayList<Bee> bees) {
        allBees.removeAll(bees);
    }

    public Player getPlayer() {
        return this.player;
    }

    public World world() {
        return world;
    }
    public ArrayList<Door> getDoors() {
        ArrayList<Door> doors = new ArrayList<Door>();
        Map grid = world.getGrid();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++) {
                Position position = new Position(world.currentLevel(), i, j);
                if (grid.get(position) instanceof Door) {
                    doors.add((Door) grid.get(position));
                }
            }
        return doors;
    }

    public boolean isSwitchLevelRequested() {
        return switchLevelRequested;
    }

    public int getSwitchLevel() {
        return switchLevel;
    }

    public void requestSwitchLevel(int level) {
        this.switchLevel = level;
        switchLevelRequested = true;
    }

    public void clearSwitchLevel() {
        switchLevelRequested = false;
    }


}
