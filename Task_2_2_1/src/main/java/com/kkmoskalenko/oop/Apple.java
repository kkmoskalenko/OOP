package com.kkmoskalenko.oop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.security.SecureRandom;

class Apple {
    private final int x;
    private final int y;

    Apple() {
        SecureRandom random = new SecureRandom();

        x = random.nextInt(Board.WIDTH);
        y = random.nextInt(Board.HEIGHT);
    }

    void draw(final GraphicsContext gc) {
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
