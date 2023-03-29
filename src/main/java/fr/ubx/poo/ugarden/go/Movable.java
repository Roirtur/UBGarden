/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go;

import fr.ubx.poo.ugarden.game.Direction;

public interface Movable {
    boolean canMove(Direction direction);
    void doMove(Direction direction);
}
