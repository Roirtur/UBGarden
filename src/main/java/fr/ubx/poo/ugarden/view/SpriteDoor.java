package fr.ubx.poo.ugarden.view;

import fr.ubx.poo.ugarden.game.State;
import fr.ubx.poo.ugarden.go.decor.Door;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteDoor extends Sprite {
    public SpriteDoor(Pane layer, Door door) {
        super(layer, null, door);
        updateImage();
    }

    @Override
    public void updateImage() {
        Door door = (Door) getGameObject();
        Image image = getImage(door.getState());
        setImage(image);
        System.out.println("SpriteDoor.updateImage()");
    }

    private Image getImage(State state) {
        return ImageResourceFactory.getInstance().getDoor(state);
    }
}
