package fr.ubx.poo.ugarden.game;

import fr.ubx.poo.ugarden.go.personage.Player;


public class Game {

    private final Configuration configuration;

    public Configuration configuration() {
        return configuration;
    }

    private final World world;
    private final Player player;

    private boolean switchLevelRequested = false;
    private int switchLevel;

    public Game(World world, Configuration configuration, Position playerPosition) {
        this.configuration = configuration;
        this.world = world;
        player = new Player(this, playerPosition);
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
