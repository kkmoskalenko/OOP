package com.kkmoskalenko.oop;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import static com.kkmoskalenko.oop.Snake.Direction.*;

class Board {
    final static int WIDTH = 20;
    final static int HEIGHT = 20;
    final static int CELL_SIZE = 20;

    private boolean inGame = true;

    private Snake snake = new Snake();
    private Apple apple = new Apple();

    void handleTick() {
        if (!inGame) {
            return;
        }

        if (snake.checkCollision()) {
            inGame = false;
            return;
        }

        if (snake.checkCollision(apple)) {
            snake.grow();
            apple = new Apple();
        }

        snake.move();
    }

    void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawGrid(gc);
        apple.draw(gc);
        snake.draw(gc);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTGRAY);

        int width = WIDTH * CELL_SIZE;
        int height = HEIGHT * CELL_SIZE;

        for (int i = 0; i <= width; i += CELL_SIZE) {
            gc.strokeLine(i, 0, i, height);
        }

        for (int i = 0; i <= height; i += CELL_SIZE) {
            gc.strokeLine(0, i, width, i);
        }
    }

    void handleEvent(KeyEvent key) {
        if (key == null) {
            return;
        }

        switch (key.getCode()) {
            case W -> snake.changeDirection(UP);
            case A -> snake.changeDirection(LEFT);
            case S -> snake.changeDirection(DOWN);
            case D -> snake.changeDirection(RIGHT);
        }
    }

    void reset() {
        snake = new Snake();
        apple = new Apple();
        inGame = true;
    }

    boolean isInGame() {
        return inGame;
    }

    int getScore() {
        return snake.getLength() - Snake.INITIAL_LENGTH;
    }
}
