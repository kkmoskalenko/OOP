package com.kkmoskalenko.oop;

import javafx.scene.paint.Color;

import java.util.Random;

public class RandomSnake extends Snake {
    private static final Random RANDOM = new Random();
    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    void move() {
        int bound = DIRECTIONS.length;
        int index = RANDOM.nextInt(bound);
        changeDirection(DIRECTIONS[index]);

        super.move();
    }

    @Override
    protected Color getHeadColor() {
        return Color.DARKGOLDENROD;
    }

    @Override
    protected Color getBodyColor() {
        return Color.GOLDENROD;
    }
}
