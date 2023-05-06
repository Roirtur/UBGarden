package fr.ubx.poo.ugarden.launcher;

import fr.ubx.poo.ugarden.game.Position;

import java.util.ArrayList;

import static fr.ubx.poo.ugarden.launcher.MapEntity.*;

public class MapLevel {

    private final int width;
    private final int height;
    private final MapEntity[][] grid;


    private Position playerPosition = null;

    public MapLevel(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MapEntity[height][width];
    }

    public int width() {
        return width;    }

    public int height() {
        return height;
    }

    public MapEntity get(int i, int j) {
        return grid[j][i];
    }

    public void set(int i, int j, MapEntity mapEntity) {
        grid[j][i] = mapEntity;
    }

    public Position getPlayerPosition() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (grid[j][i] == Player) {
                    if (playerPosition != null)
                        throw new RuntimeException("Multiple definition of player");
                    set(i, j, Grass);
                    // Player can be only on level 1
                    playerPosition = new Position(1, i, j);
                }
            }
        return playerPosition;
    }

    public ArrayList<Position> getBeePositions(int level) {
        ArrayList<Position> beePositions = new ArrayList<Position>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (grid[j][i] == Bee) {
                    beePositions.add(new Position(level, i, j));
                }
            }
        return beePositions;
    }


    public boolean hasEndDoor() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i] == DoorNextClosed || grid[j][i] == DoorNextOpened) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasPrincess() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i] == Princess) {
                    return true;
                }
            }
        }
        return false;
    }

    public void replaceDoorByPrincess() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i] == DoorNextClosed || grid[j][i] == DoorNextOpened) {
                    grid[j][i] = Princess;
                }
            }
        }
    }
}
