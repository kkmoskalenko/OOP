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

public final class MainApp extends Application {
    private final Board board = new Board();

    private final Group root = new Group();
    private final Canvas canvas = new Canvas();
    private final Label label = new Label();
    private final VBox gameOverUI = new VBox();

    private AnimationTimer timer;
    private KeyEvent lastPressedKey;

    // JavaFX UI constants
    private static final int SCORE_LABEL_FONT_SIZE = 20;
    private static final int SCORE_LABEL_PADDING_INSETS = 8;
    private static final int GAME_OVER_SCREEN_SPACING = 15;
    private static final double GAME_OVER_SCREEN_BACKGROUND_OPACITY = 0.66;
    private static final int GAME_OVER_LABEL_FONT_SIZE = 36;
    private static final int GAME_OVER_BUTTON_FONT_SIZE = 16;
    private static final int GAME_OVER_BUTTON_PADDING_INSETS = 10;

    @Override
    public void start(final Stage stage) {
        Scene scene = new Scene(root, Color.WHITESMOKE);

        root.getChildren().add(canvas);
        root.getChildren().add(label);

        canvas.setWidth(Board.WIDTH * Board.CELL_SIZE);
        canvas.setHeight(Board.HEIGHT * Board.CELL_SIZE);

        label.setFont(Font.font("Courier", SCORE_LABEL_FONT_SIZE));
        label.setPadding(new Insets(SCORE_LABEL_PADDING_INSETS));

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
            private static final int DRAWING_DELAY = 140 * 1_000_000;
            private long lastUpdate;

            @Override
            public void handle(final long now) {
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
        gameOverUI.setSpacing(GAME_OVER_SCREEN_SPACING);
        gameOverUI.setAlignment(Pos.CENTER);
        gameOverUI.setPrefWidth(canvas.getWidth());
        gameOverUI.setPrefHeight(canvas.getHeight());
        gameOverUI.setBackground(new Background(new BackgroundFill(
                Color.grayRgb(0, GAME_OVER_SCREEN_BACKGROUND_OPACITY), null, null
        )));
        gameOverUI.setVisible(false);

        Label gameOverLabel = new Label("Game over");
        gameOverLabel.setFont(Font.font("Courier", FontWeight.BOLD, GAME_OVER_LABEL_FONT_SIZE));
        gameOverLabel.setTextFill(Color.INDIANRED);

        Button retryButton = new Button("Retry");
        retryButton.setFont(Font.font("Courier", GAME_OVER_BUTTON_FONT_SIZE));
        retryButton.setPadding(new Insets(GAME_OVER_BUTTON_PADDING_INSETS));
        retryButton.setOnAction(event -> {
            lastPressedKey = null;
            gameOverUI.setVisible(false);

            board.reset();
            timer.start();
        });

        gameOverUI.getChildren().addAll(gameOverLabel, retryButton);
        root.getChildren().add(gameOverUI);
    }
}
