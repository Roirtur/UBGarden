package fr.ubx.poo.ugarden.view;

import fr.ubx.poo.ugarden.engine.GameEngine;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.launcher.GameLauncher;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class GameLauncherView extends BorderPane {
    private final FileChooser fileChooser = new FileChooser();

    public GameLauncherView(Stage stage)  {
        // Create menu
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem loadItem = new MenuItem("Load from file ...");
        MenuItem defaultItemStart = new MenuItem("Load default start configuration");
        MenuItem defaultItem = new MenuItem("Load default configuration");
        MenuItem loadItemF = new MenuItem("Load from file");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuFile.getItems().addAll(
                loadItem, defaultItem, defaultItemStart, new SeparatorMenuItem(),
                loadItemF, new SeparatorMenuItem(),
                exitItem);

        menuBar.getMenus().addAll(menuFile);
        this.setTop(menuBar);

        Text text = new Text("UBGarden 2023");
        text.getStyleClass().add("message");
        VBox scene = new VBox();
        scene.getChildren().add(text);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        scene.getStyleClass().add("message");
        this.setCenter(scene);

        // Load from file
        loadItem.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                // TODO
                System.err.println("[TODO] Not implemented");
            }
        });

        defaultItem.setOnAction(e -> {
            Game game = GameLauncher.getInstance().loadDefault(0);
            GameEngine engine = new GameEngine(game, stage);
            engine.start();
        });

        defaultItemStart.setOnAction(e -> {
            Game game = GameLauncher.getInstance().loadDefault(1);
            GameEngine engine = new GameEngine(game, stage);
            engine.start();
        });

        loadItemF.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                // Chargement depuis un fichier (avec compression)
                Game game = GameLauncher.getInstance().loadFile(file);
                GameEngine engine = new GameEngine(game, stage);
                engine.start();
            }
        });

        // Exit
        exitItem.setOnAction(e -> System.exit(0));

    }


}
