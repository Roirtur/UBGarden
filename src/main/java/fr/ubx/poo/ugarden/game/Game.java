package fr.ubx.poo.ugarden.game;

import fr.ubx.poo.ugarden.go.personage.Bee;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.personage.Player;
import fr.ubx.poo.ugarden.view.SpriteBee;

import java.util.ArrayList;


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

    public Game(World world, Configuration configuration, Position playerPosition, ArrayList<Position> beePositions) {
        this.configuration = configuration;
        this.world = world;
        player = new Player(this, playerPosition);
        for (Position position : beePositions) {
            this.allBees.add(new Bee(this, position));
        }
    }

    public ArrayList<Bee> getBees() {return this.allBees;}

    public void deleteBee(ArrayList<Bee> bees) {
        allBees.removeAll(bees);
    }

    public Player getPlayer() {
        return this.player;
    }

    public World world() {
        return world;
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
