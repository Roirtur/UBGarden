package fr.ubx.poo.ugarden.launcher;


import fr.ubx.poo.ugarden.game.Position;

import static fr.ubx.poo.ugarden.launcher.MapEntity.*;

public class MapLevelDefault extends MapLevel {

    private final MapEntity[][] level1 = {
            {Grass, Grass, Grass, Grass, Grass, Land, Land, Land, Flowers, Grass, Grass, Grass, Grass, Carrots, Grass, Grass, Bee, Grass},
            {Grass, Player, Grass, Grass, Grass, Land, Land, Land, Apple, Tree, Grass, Grass, Tree, Tree, Apple, Carrots, Grass, Grass},
            {Grass, Grass, Grass, Grass, Grass, Land, Land, Land, Flowers, Flowers, Flowers, Grass, Grass, Tree, Carrots, Carrots, Carrots, Carrots},
            {Grass, Grass, Grass, Grass, Grass, Land, Land, Land, Carrots, Bee, Carrots, Carrots, Grass, Tree, Flowers, Carrots, Carrots, Flowers},
            {PoisonedApple, Tree, Grass, Tree, Grass, Grass, Flowers, Flowers, Carrots, Carrots, Carrots, Carrots, Grass, Tree, Flowers, Carrots, Heart, Flowers},
            {Grass, Tree, Tree, Tree, PoisonedApple, Flowers, Grass, Grass, Carrots, Carrots, Carrots, Carrots, Grass, Grass, Flowers, Flowers, Flowers, Flowers},
            {Grass, Grass, Grass, PoisonedApple, Grass, Grass, Grass, Grass, Carrots, Carrots, Carrots, Carrots, Bee, Grass, Grass, Grass, Princess, Grass},
            {Grass, Tree, Grass, Tree, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass, Grass}
    };

    private final static int width = 18;
    private final static int height = 8;

    public MapLevelDefault() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                set(i, j, level1[j][i]);    }
}
