package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.Walkable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.personage.Player;
public class Apple extends Bonus {
    public Apple(Position position, Decor decor) {
        super(position, decor);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }
}
