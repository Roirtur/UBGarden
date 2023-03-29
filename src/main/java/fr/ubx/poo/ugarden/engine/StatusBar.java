/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.engine;

import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.go.personage.Player;
import fr.ubx.poo.ugarden.view.ImageResource;
import fr.ubx.poo.ugarden.view.ImageResourceFactory;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StatusBar {
    public static final int height = 55;
    private final Text lives = new Text();
    private final Text keys = new Text();
    private final HBox level = new HBox();

    private final Text energy = new Text();

    private final Text diseaseLevel = new Text();
    private final int gameLevel = 1;
    private final DropShadow ds = new DropShadow();


    public StatusBar(Group root, int sceneWidth, int sceneHeight) {
        // Status bar
        level.getStyleClass().add("level");
        level.getChildren().add(new ImageView(ImageResourceFactory.getInstance().getDigit(gameLevel)));

        ds.setRadius(5.0);
        ds.setOffsetX(3.0);
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.5f, 0.5f, 0.5f));


        HBox status = new HBox();
        status.getStyleClass().add("status");
        HBox livesStatus = statusGroup(ImageResourceFactory.getInstance().get(ImageResource.HEART), lives);
        HBox keysStatus = statusGroup(ImageResourceFactory.getInstance().get(ImageResource.KEY), keys);
        HBox energyStatus = statusGroup(ImageResourceFactory.getInstance().get(ImageResource.ENERGY), energy);
        HBox diseaseLevelStatus = statusGroup(ImageResourceFactory.getInstance().get(ImageResource.POISONED_APPLE), diseaseLevel);

        status.setSpacing(40.0);
        status.getChildren().addAll(livesStatus, diseaseLevelStatus, keysStatus, energyStatus);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(level, status);
        hBox.getStyleClass().add("statusBar");
        hBox.relocate(0, sceneHeight);
        hBox.setPrefSize(sceneWidth, height);
        root.getChildren().add(hBox);
    }


    private HBox statusGroup(Image kind, Text number) {
        HBox group = new HBox();
        ImageView img = new ImageView(kind);
        group.setSpacing(4);
        number.setEffect(ds);
        number.setCache(true);
        number.setFill(Color.BLACK);
        number.getStyleClass().add("number");
        group.getChildren().addAll(img, number);
        return group;
    }

    public void update(Game game) {
        lives.setText("?");
        keys.setText("?");
        energy.setText("?");
        diseaseLevel.setText("x?");
    }

}
