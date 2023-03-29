/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.personage.Player;

public class Heart extends Bonus {

    public Heart(Position position, Decor decor) {
        super(position, decor);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }
}
