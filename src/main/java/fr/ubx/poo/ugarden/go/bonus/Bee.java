package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.engine.Timer;
import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.game.Map;
import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Movable;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.Walkable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.decor.Tree;
import fr.ubx.poo.ugarden.go.personage.Player;

public class Bee extends Bonus implements Movable {

    private boolean canMove;
    private Direction direction;
    private Timer timer;
    private Game game;

    public Direction getDirection() { return direction; }

    public Bee(Game game, Position position, Decor decor) {
        super(position, decor);
        this.game = game;
        this.direction = Direction.random();
        this.timer = new Timer(game.configuration().beeMoveFrequency());
        this.timer.start();
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    public void update(long now) {
        timer.update(now);
        if (!timer.isRunning()) {
            Direction nextDirection = Direction.random();
            this.direction = nextDirection;
            if (canMove(nextDirection)) {
                setModified(true);
                doMove(nextDirection);
            }
            timer.start();
        }
    }

    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        Map grid = game.world().getGrid();
        if (!grid.inside(nextPos)) return false;
        Decor next = grid.get(nextPos);
        return !(next instanceof Tree);
    }

    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

}
