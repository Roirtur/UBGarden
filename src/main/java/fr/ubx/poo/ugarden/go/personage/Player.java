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
import fr.ubx.poo.ugarden.go.decor.*;
import fr.ubx.poo.ugarden.go.bonus.*;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.decor.ground.Land;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Player extends GameObject implements Movable, TakeVisitor, WalkVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private int energy;
    private int keys = 0;
    private int diseaseLevel = 1;
    private final Timer timer;
    private int lives;
    private ArrayList<Timer> diseasesTimeSave = new ArrayList<>();
    private boolean isInvincible;
    private Timer inivncibilityTimer;
    private boolean canEnterDoor = false;
    private Timer doorTimer;

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = game.configuration().playerLives();
        this.energy = game.configuration().playerEnergy();
        this.timer = new Timer(game.configuration().energyRecoverDuration());
        this.inivncibilityTimer = new Timer(game.configuration().playerInvincibilityDuration());
        this.doorTimer = new Timer(2);
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
        checkDiseases(now);
        checkInvincibility(now);
        checkDoorTimer(now);
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
        Timer timer = new Timer(game.configuration().diseaseDuration());
        timer.start();
        diseasesTimeSave.add(timer);
        bonus.remove();
    }
    @Override
    public void take(Apple bonus) {
        gainEnergy(game.configuration().energyBoost());
        diseaseLevel = 1;
        diseasesTimeSave.clear();
        bonus.remove();
    }
    @Override
    public void take(Key bonus) {
        gainKey();
        bonus.remove();
    }
    @Override
    public final boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        Map map = game.world().getGrid();
        if (!map.inside(nextPos))
            return false;
        else return !(map.get(nextPos) instanceof Tree) && !(map.get(nextPos) instanceof Flowers) && (energy- loseEnergy() >= 0);
    }

    public boolean isOnPrincess() {
        Map map = game.world().getGrid();
        return (map.get(getPosition()) instanceof Princess);
    }

    public boolean isOnDoorWithKey() {
        Map map = game.world().getGrid();
        return (map.get(getPosition()) instanceof Door) && getKeys() > 0 && !(((Door) map.get(getPosition())).isOpen());
    }

    public boolean isOnOpenedDoor() {
        Map map = game.world().getGrid();
        return ((map.get(getPosition()) instanceof Door) && ((Door) map.get(getPosition())).isOpen());
    }
    private void showVictory() {
        Text text = new Text("Victory");
        text.getStyleClass().add("message");
        VBox scene = new VBox();
        scene.getChildren().add(text);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        scene.getStyleClass().add("message");
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
    public void loseKey() {
        keys -= 1;
    }

    private void gainDisease() {
        diseaseLevel += 1;
    }
    private void loseDisease() {
        diseaseLevel -= 1;
    }

    private void checkDiseases(long now) {
        if (diseasesTimeSave.isEmpty()) {
            return;
        }

        ArrayList<Timer> toRemove = new ArrayList<>();
        for (Timer timer : diseasesTimeSave) {
            timer.update(now);
            if (!timer.isRunning()) {
                toRemove.add(timer);
                loseDisease();
            }
        }

        diseasesTimeSave.removeAll(toRemove);
    }

    public void setInvincible() {
        isInvincible = true;
        inivncibilityTimer.start();
    }

    public boolean getInvincible() {
        return isInvincible;
    }

    private void checkInvincibility(long now) {
        if (isInvincible) {
            if (inivncibilityTimer.isRunning()) {
                inivncibilityTimer.update(now);
            } else {
                isInvincible = false;
                setModified(true);
            }
        }
    }

    public void setDoorTimer() {
        canEnterDoor = false;
        doorTimer.start();
    }
    public boolean canEnterDoor() { return canEnterDoor; }

    private void checkDoorTimer(long now) {
        if (!canEnterDoor) {
            if (doorTimer.isRunning()) {
                doorTimer.update(now);
            } else {
                canEnterDoor = true;
            }
        }
    }

    @Override
    public String toString() {
        return "Player";
    }


}
