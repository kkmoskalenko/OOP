package com.kkmoskalenko.oop;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application {
    private final Board board = new Board();

    private final Group root = new Group();
    private final Canvas canvas = new Canvas();
    private final Label label = new Label();
    private final VBox gameOverUI = new VBox();

    private AnimationTimer timer;
    private KeyEvent lastPressedKey;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, Color.WHITESMOKE);

        root.getChildren().add(canvas);
        root.getChildren().add(label);

        canvas.setWidth(Board.WIDTH * Board.CELL_SIZE);
        canvas.setHeight(Board.HEIGHT * Board.CELL_SIZE);

        label.setFont(Font.font("Courier", 20));
        label.setPadding(new Insets(8));

        stage.setScene(scene);
        stage.setTitle("Snake");
        stage.setResizable(false);
        stage.addEventFilter(
                KeyEvent.KEY_PRESSED,
                key -> lastPressedKey = key);
        stage.show();

        setupTimer();
        setupGameOverUI();
    }

    private void setupTimer() {
        timer = new AnimationTimer() {
            private final static int DRAWING_DELAY = 140 * 1_000_000;
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < DRAWING_DELAY) {
                    return;
                }

                if (board.isInGame()) {
                    board.handleEvent(lastPressedKey);
                    board.handleTick();
                    board.draw(canvas);

                    label.setText("Score: " + board.getScore());
                } else {
                    stop();
                    gameOverUI.setVisible(true);
                }

                lastUpdate = now;
            }
        };

        timer.start();
    }

    private void setupGameOverUI() {
        gameOverUI.setSpacing(15);
        gameOverUI.setAlignment(Pos.CENTER);
        gameOverUI.setPrefWidth(canvas.getWidth());
        gameOverUI.setPrefHeight(canvas.getHeight());
        gameOverUI.setBackground(new Background(new BackgroundFill(
                Color.grayRgb(0, 0.66), null, null
        )));
        gameOverUI.setVisible(false);

        Label label = new Label("Game over");
        label.setFont(Font.font("Courier", FontWeight.BOLD, 36));
        label.setTextFill(Color.INDIANRED);

        Button button = new Button("Retry");
        button.setFont(Font.font("Courier", 16));
        button.setPadding(new Insets(10));
        button.setOnAction(event -> {
            lastPressedKey = null;
            gameOverUI.setVisible(false);

            board.reset();
            timer.start();
        });

        gameOverUI.getChildren().addAll(label, button);
        root.getChildren().add(gameOverUI);
    }
}
