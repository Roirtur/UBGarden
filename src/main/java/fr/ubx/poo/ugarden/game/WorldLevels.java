package fr.ubx.poo.ugarden.game;


import java.util.*;

public class WorldLevels implements World {
    private final java.util.Map<Integer, Map> grids = new HashMap<>();
    private final int levels;
    private int currentLevel = 1;

    public WorldLevels(int levels) {
        if (levels < 1) throw new IllegalArgumentException("Levels must be greater than 1");
        this.levels = levels;
    }

    @Override
    public int levels() {
        return levels;
    }

    @Override
    public int currentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    @Override
    public Map getGrid(int level) {
        return grids.get(level);
    }

    @Override
    public Map getGrid() {
        return getGrid(currentLevel);
    }

    public void put(int level, Map grid) {
        this.grids.put(level, grid);
    }

}
