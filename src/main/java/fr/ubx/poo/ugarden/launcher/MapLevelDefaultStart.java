package fr.ubx.poo.ugarden.launcher;


import static fr.ubx.poo.ugarden.launcher.MapEntity.*;

public class MapLevelDefaultStart extends MapLevel {

    private final MapEntity[][] level1 = {
            {Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass},
            {Grass, Player, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Tree, Grass, Grass, Tree, Tree, Grass, Grass, Grass, Grass},
            {Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Tree, Grass, Grass, Grass, Grass},
            {Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Tree, Grass, Grass, Grass, Grass},
            {Grass, Tree, Grass, Tree, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Tree, Grass, Grass, Heart, Grass},
            {Grass, Tree, Tree, Tree, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass},
            {Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass},
            {Grass, Tree, Grass, Tree, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass}
    };

    private final static int width = 18;
    private final static int height = 8;

    public MapLevelDefaultStart() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (level1[j][i] == Player) {
                    set(i, j, Grass);
                } else {
                    set(i, j, level1[j][i]);
                }
            }
    }
}
