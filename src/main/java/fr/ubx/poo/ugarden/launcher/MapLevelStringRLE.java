package fr.ubx.poo.ugarden.launcher;


import fr.ubx.poo.ugarden.game.Position;

import static fr.ubx.poo.ugarden.launcher.MapEntity.*;

public class MapLevelStringRLE extends MapLevel {

    public MapLevelStringRLE(String[] lines,int  width,int height) {
        super(width, height);
        for (int j = 0; j < height; j++) {
            int i = 0;
            for (int k = 0; k < lines[j].length(); k++) {
                char c = lines[j].charAt(k);
                if (Character.isDigit(c)) {
                    int count = Character.getNumericValue(c);
                    for (int l = 0; l < count - 1; l++) {
                        set(i, j, MapEntity.fromCode(lines[j].charAt(k - 1)));
                        i++;
                    }
                } else {
                    set(i, j, MapEntity.fromCode(c));
                    i++;
                }
            }
        }
    }
}
