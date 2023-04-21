package fr.ubx.poo.ugarden.go.decor;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.game.State;

public class Door extends Decor {
    private State state = State.CLOSED;

    public boolean isOpen() {
        return state == State.OPENED;
    }
    public void setOpen() {
        state = State.OPENED;
        setModified(true);
    }
    public State getState() { return state; }
    public Door(Position position, State state) {
        super(position);
        this.state = state;
    }
}