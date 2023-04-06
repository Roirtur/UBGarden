/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go.personage;

import fr.ubx.poo.ugarden.engine.Timer;
import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.game.Map;
import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Movable;
import fr.ubx.poo.ugarden.go.TakeVisitor;
import fr.ubx.poo.ugarden.go.WalkVisitor;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.bonus.*;
import fr.ubx.poo.ugarden.go.decor.Flowers;
import fr.ubx.poo.ugarden.go.decor.Tree;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.decor.ground.Land;

public class Player extends GameObject implements Movable, TakeVisitor, WalkVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private int energy;
    private int keys = 0;
    private int diseaseLevel = 1;
    private Timer timer;
    private int lives;

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = game.configuration().playerLives();
        this.energy = game.configuration().playerEnergy();
        this.timer = new Timer(game.configuration().energyRecoverDuration());
        timer.start();
    }

    public int getKeys() { return keys; }
    public int getDiseaseLevel() { return diseaseLevel; }
    public int getEnergy() { return energy; }
    public int getLives() {
        return lives;
    }
    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public void update(long now) {
        timer.update(now);
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
            moveRequested = false;
            timer.reset();
        }
        gainPassiveEnergy();
    }

    @Override
    public void take(Heart bonus) {
        gainLife();
        bonus.remove();
    }
    @Override
    public void take(PoisonedApple bonus) {
        gainDisease();
        bonus.remove();
    }
    @Override
    public void take(Apple bonus) {
        gainEnergy(game.configuration().energyBoost());
        diseaseLevel = 1;
        bonus.remove();
    }
    @Override
    public void take(Key bonus) {
        gainKey();
        bonus.remove();
        System.out.println("I wonder what it's opening [TO DO]");
    }
    @Override
    public final boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        Map map = game.world().getGrid();
        if (!map.inside(nextPos))
            return false;
        else return !(map.get(nextPos) instanceof Tree) && !(map.get(nextPos) instanceof Flowers) && (energy- loseEnergy() >= 0);
   }

    @Override
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        Decor next = game.world().getGrid().get(nextPos);
        energy -= loseEnergy();
        setPosition(nextPos);
        if (next != null)
            next.takenBy(this);
    }

    private int loseEnergy() {
        Map map = game.world().getGrid();
        if(map.get(getPosition()) instanceof Grass)
            return getDiseaseLevel();
        else if (map.get(getPosition()) instanceof Land)
            return 2*getDiseaseLevel();
        else
            return 3*getDiseaseLevel();
    }

    private void gainPassiveEnergy() {
        if (!timer.isRunning()) {
            gainEnergy(1);
            timer.start();
        }
    }

    private void gainEnergy(int value) {
        int new_value = energy + value;
        energy = Math.min(new_value, 100);
    }

    public void loseLife() {
        lives -= 1;
    }

    private void gainLife() {
        lives += 1;
    }

    private void gainKey() {
        keys += 1;
    }

    private void gainDisease() {
        diseaseLevel += 1;
    }

    @Override
    public String toString() {
        return "Player";
    }


}
