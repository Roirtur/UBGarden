/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.Walkable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.personage.Player;

public abstract class Bonus extends GameObject implements Takeable {

    private final Decor decor;

    public Bonus(Position position, Decor decor) {
        super(position);
        this.decor = decor;
    }

    @Override
    public void remove() {
        super.remove();
        decor.setBonus(null);
    }

}
