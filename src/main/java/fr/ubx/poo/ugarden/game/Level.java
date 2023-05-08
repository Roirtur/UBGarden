package fr.ubx.poo.ugarden.game;

import fr.ubx.poo.ugarden.go.bonus.*;
import fr.ubx.poo.ugarden.go.decor.ground.*;
import fr.ubx.poo.ugarden.go.decor.*;
import fr.ubx.poo.ugarden.launcher.MapEntity;
import fr.ubx.poo.ugarden.launcher.MapLevel;

import java.util.*;

public class Level implements Map {

    private final int level;
    private final int width;

    private final int height;

    private final java.util.Map<Position, Decor> decors = new HashMap<>();

    public Level(int level, MapLevel entities) {

        this.level = level;
        this.width = entities.width();
        this.height = entities.height();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(level, i, j);
                MapEntity mapEntity = entities.get(i, j);
                switch (mapEntity) {
                    case Grass, Bee -> {
                        decors.put(position, new Grass(position));
                    }
                    case Tree -> {
                        decors.put(position, new Tree(position));
                    }
                    case Heart -> {
                        Decor grass = new Grass(position);
                        grass.setBonus(new Heart(position, grass));
                        decors.put(position, grass);
                    }
                    case PoisonedApple -> {
                        Decor grass = new Grass(position);
                        grass.setBonus(new PoisonedApple(position, grass));
                        decors.put(position, grass);
                    }
                    case Land -> {
                        decors.put(position, new Land(position));
                    }
                    case Carrots -> {
                        decors.put(position, new Carrots(position));
                    }
                    case Flowers -> {
                        decors.put(position, new Flowers(position));
                    }
                    case Apple -> {
                        Decor grass = new Grass(position);
                        grass.setBonus(new Apple(position, grass));
                        decors.put(position, grass);
                    }
                    case Princess -> {
                        decors.put(position, new Princess(position));
                    }
                    case DoorNextClosed, DoorNextOpened -> {
                        decors.put(position, new Door(position, State.CLOSED));
                    }
                    case DoorPrevOpened -> {
                        if (level == 1) {
                            throw new RuntimeException("Can't have " + mapEntity.name() + " on the first level");
                        }
                        decors.put(position, new Door(position, State.OPENED));
                    }
                    case Key -> {
                        Decor grass = new Grass(position);
                        grass.setBonus(new Key(position, grass));
                        decors.put(position, grass);
                    }
                    default -> throw new RuntimeException("EntityCode " + mapEntity.name() + " not processed");
                }
            }
    }
    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    public Decor get(Position position) {
        return decors.get(position);
    }

    @Override
    public void remove(Position position) {
        decors.remove(position);
    }

    public Collection<Decor> values() {
        return decors.values();
    }


    @Override
    public boolean inside(Position position) {
        return position.level() == level && position.x() >= 0 && position.x() < width && position.y() >= 0 && position.y() < height;
    }

    @Override
    public void set(Position position, Decor decor) {
        if (!inside(position))
            throw new IllegalArgumentException("Illegal Position");
        if (decor != null)
            decors.put(position, decor);
    }


}
