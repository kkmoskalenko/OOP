package com.kkmoskalenko.oop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

class Apple {
    private final static Random random = new Random();
    private final int x, y;

    Apple() {
        x = random.nextInt(Board.WIDTH);
        y = random.nextInt(Board.HEIGHT);
    }

    void draw(GraphicsContext gc) {
        int size = Board.CELL_SIZE;

        gc.setFill(Color.FIREBRICK);
        gc.fillRect(x * size, y * size, size, size);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
