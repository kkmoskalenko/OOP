package com.kkmoskalenko.oop;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

class Board {
    static final int WIDTH = 20;
    static final int HEIGHT = 20;
    static final int CELL_SIZE = 20;

    private boolean inGame = true;

    private Snake snake = new Snake();
    private Apple apple = new Apple();

    private RandomSnake randomSnake = new RandomSnake();
    private GreedySnake greedySnake = new GreedySnake();

    void handleTick() {
        if (!inGame) {
            return;
        }

        if (snake.checkCollision()) {
            inGame = false;
            return;
        }

        if (randomSnake.checkCollision()) {
            randomSnake = new RandomSnake();
        }

        if (greedySnake.checkCollision()) {
            greedySnake = new GreedySnake();
        }

        if (snake.checkCollision(apple)) {
            snake.grow();
            apple = new Apple();
        }

        if (randomSnake.checkCollision(apple)) {
            randomSnake.grow();
            apple = new Apple();
        }

        if (greedySnake.checkCollision(apple)) {
            greedySnake.grow();
            apple = new Apple();
        }

        snake.move();
        randomSnake.move();
        greedySnake.move(apple);
    }

    void draw(final Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawGrid(gc);
        apple.draw(gc);
        snake.draw(gc);
        randomSnake.draw(gc);
        greedySnake.draw(gc);
    }

    private static void drawGrid(final GraphicsContext gc) {
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

    void handleEvent(final KeyEvent key) {
        if (key == null) {
            return;
        }

        switch (key.getCode()) {
            case W -> snake.changeDirection(Snake.Direction.UP);
            case A -> snake.changeDirection(Snake.Direction.LEFT);
            case S -> snake.changeDirection(Snake.Direction.DOWN);
            case D -> snake.changeDirection(Snake.Direction.RIGHT);
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
