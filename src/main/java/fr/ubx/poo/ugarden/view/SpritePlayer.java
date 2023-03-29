/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.view;

import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.go.personage.Player;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpritePlayer extends Sprite {
    private final ColorAdjust effect = new ColorAdjust();

    public SpritePlayer(Pane layer, Player player) {
        super(layer, null, player);
        // effect.setBrightness(0.5);
        updateImage();
    }

    @Override
    public void updateImage() {
        Player player = (Player) getGameObject();
        Image image = getImage(player.getDirection());
        setImage(image);
    }

    private Image getImage(Direction direction) {
        return ImageResourceFactory.getInstance().getPlayer(direction);
    }
}
