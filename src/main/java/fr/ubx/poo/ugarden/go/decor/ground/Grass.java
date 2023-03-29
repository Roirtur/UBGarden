/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go.decor.ground;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.bonus.Bonus;
import fr.ubx.poo.ugarden.go.personage.Player;

public class Grass extends Ground {
    public Grass(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        Bonus bonus = getBonus();
        if (bonus != null) {
            bonus.takenBy(player);
        }
    }

    @Override
    public int energyConsumptionWalk() { return 1; }

}
