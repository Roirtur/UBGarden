package fr.ubx.poo.ugarden.game;

import fr.ubx.poo.ugarden.go.bonus.*;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.decor.*;
import fr.ubx.poo.ugarden.launcher.MapEntity;
import fr.ubx.poo.ugarden.launcher.MapLevel;

import java.util.*;

public class Level implements Map {

    private final int level;
    private final int width;

    private final int height;

    private final MapLevel entities;

    private final java.util.Map<Position, Decor> decors = new HashMap<>();

    public Level(Game game, int level, MapLevel entities) {
        this.level = level;
        this.entities = entities;
        this.width = entities.width();
        this.height = entities.height();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(level, i, j);
                MapEntity mapEntity = entities.get(i, j);
                switch (mapEntity) {
                    case Grass:
                        decors.put(position, new Grass(position));
                        break;
                    case Tree:
                        decors.put(position, new Tree(position));
                        break;
                    case Heart: {
                        Decor grass = new Grass(position);
                        grass.setBonus(new Heart(position, grass));
                        decors.put(position, grass);
                        break;
                    }
                    default:
                        throw new RuntimeException("EntityCode " + mapEntity.name() + " not processed");
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
