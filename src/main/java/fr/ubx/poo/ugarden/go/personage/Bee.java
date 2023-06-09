package fr.ubx.poo.ugarden.go.personage;

import fr.ubx.poo.ugarden.engine.Timer;
import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.game.Map;
import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Movable;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.decor.Tree;

public class Bee extends GameObject implements Movable, Takeable {

    private boolean canMove;
    private Direction direction;
    private Timer timer;
    private Game game;

    public Direction getDirection() { return direction; }

    public Bee(Game game, Position position) {
        super(position);
        this.game = game;
        this.direction = Direction.random();
        this.timer = new Timer(1/(double)game.configuration().beeMoveFrequency());
        this.timer.start();
    }

    @Override
    public void remove() {
        super.remove();
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
            timer.reset();
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
