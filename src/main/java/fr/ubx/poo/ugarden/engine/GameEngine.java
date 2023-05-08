/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.engine;

import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.game.State;
import fr.ubx.poo.ugarden.go.decor.Door;
import fr.ubx.poo.ugarden.go.personage.Bee;
import fr.ubx.poo.ugarden.go.personage.Player;
import fr.ubx.poo.ugarden.view.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final Game game;
    private final Player player;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;
    private StatusBar statusBar;
    private Pane layer;
    private Input input;

    public GameEngine(Game game, final Stage stage) {
        this.stage = stage;
        this.game = game;
        this.player = game.getPlayer();
        initialize();
        buildAndSetGameLoop();
    }

    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        int height = game.world().getGrid().height();
        int width = game.world().getGrid().width();
        int sceneWidth = width * ImageResource.size;
        int sceneHeight = height * ImageResource.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(game, root, sceneWidth, sceneHeight);

        // Create sprites

        for (var decor : game.world().getGrid().values()) {
            sprites.add(SpriteFactory.create(layer, decor));
            decor.setModified(true);
            var bonus = decor.getBonus();
            if (bonus != null) {
                sprites.add(SpriteFactory.create(layer, bonus));
                bonus.setModified(true);
            }
        }

        sprites.add(new SpritePlayer(layer, player));
        ArrayList<Bee> allBees = game.getBees();
        for (Bee bee : allBees) {
            sprites.add(new SpriteBee(layer, bee));
        }
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
            checkLevel();

            // Check keyboard actions
            processInput(now);

            // Do actions
            update(now);
            checkCollision(now);

            // Graphic update
            cleanupSprites();
            render();
            statusBar.update(game);
            }
        };
    }


    private void checkLevel() {
        if (game.isSwitchLevelRequested()) {
            // Find the new level to switch to
            if (game.getSwitchLevel() == game.world().levels() + 1) {
                //error no level found
                System.out.println("Error: No level found");
            }
            // chose the spawn of the player
            boolean spawnNext = true;
            if (game.world().currentLevel() == game.getSwitchLevel() + 1) {
                spawnNext = false;
            }
            // clear all sprites
            sprites.clear();
            // change the current level
            game.world().setCurrentLevel(game.getSwitchLevel());
            // Find the position of the door to reach
            ArrayList<Door> doors = game.getDoors();
            for (Door door : doors) {
                if (door.getDirection() == State.NEXT) {
                    if (!spawnNext) {
                        player.setPosition(door.getPosition());
                    }
                }
            // Set the position of the player
                if (spawnNext && door.getDirection() == State.PREVIOUS) {
                    player.setPosition(door.getPosition());
                }
            }
            game.loadBees();
            initialize();
            game.clearSwitchLevel();
        }
    }

    public void deleteBee(ArrayList<Bee> bees) {
        for (Bee bee : bees) {
            bee.remove();
        }
        game.deleteBee(bees);
    }

    private void checkCollision(long now) {
        ArrayList<Bee> bees = game.getBees();
        ArrayList<Bee> removeBees = new ArrayList<>();
        for (Bee bee : bees) {
            if (!player.getInvincible() && player.getPosition().equals(bee.getPosition())) {
                player.loseLife();
                player.setInvincible();
                // Bee dies
                removeBees.add(bee);
            }
        }
        deleteBee(removeBees);
    }

    private Door getDoorCollision() {
        ArrayList<Door> doors = game.getDoors();
        for (Door door : doors) {
            if (player.getPosition().equals(door.getPosition())) {
                return door;
            }
        }
        return null;
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    private void update(long now) {
        player.update(now);

        ArrayList<Bee> bees = game.getBees();
        for (Bee bee : bees) {
            bee.update(now);
        }

        if (player.getLives() == 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        else if (player.isOnPrincess()) {
            gameLoop.stop();
            showMessage("Victoire!", Color.RED);
        }
        else if (player.isOnDoorWithKey()) {
            Door door = getDoorCollision();
            if (!door.isOpen()) {
                door.setOpen();
                player.loseKey();
            }
        }
        else if (player.isOnOpenedDoor() && player.canEnterDoor()) {
            Door door = getDoorCollision();
            if (door.getDirection() == State.NEXT) {
                game.requestSwitchLevel(game.world().currentLevel() + 1);
            }
            else if (door.getDirection() == State.PREVIOUS) {
                game.requestSwitchLevel(game.world().currentLevel() - 1);
            }
            player.setDoorTimer();
        }

    }

    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }
}