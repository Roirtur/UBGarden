package fr.ubx.poo.ugarden.launcher;


import fr.ubx.poo.ugarden.game.Position;

import static fr.ubx.poo.ugarden.launcher.MapEntity.*;

public class MapLevelString extends MapLevel {

    public MapLevelString(String[] lines,int  width,int height) {
        super(width, height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                set(i, j, MapEntity.fromCode(lines[j].charAt(i)));
            }
        }
    }
}
