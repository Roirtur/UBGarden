/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.view;

import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.go.personage.Bee;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteBee extends Sprite {
    private final ColorAdjust effect = new ColorAdjust();

    public SpriteBee(Pane layer, Bee bee) {
        super(layer, null, bee);
        updateImage();
    }

    @Override
    public void updateImage() {
        Bee bee = (Bee) getGameObject();
        Image image = getImage(bee.getDirection());
        setImage(image);
    }

    private Image getImage(Direction direction) {
        return ImageResourceFactory.getInstance().getBee(direction);
    }
}
