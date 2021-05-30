package com.kkmoskalenko.oop;

import javafx.scene.paint.Color;

final class GreedySnake extends Snake {
    void move(final Apple apple) {
        Joint head = getHead();

        if (head.getX() != apple.getX()) {
            changeDirection(head.getX() < apple.getX()
                    ? Direction.RIGHT : Direction.LEFT);
        }

        if (head.getY() != apple.getY()) {
            changeDirection(head.getY() < apple.getY()
                    ? Direction.DOWN : Direction.UP);
        }

        super.move();
    }

    @Override
    protected Color getHeadColor() {
        return Color.INDIANRED;
    }

    @Override
    protected Color getBodyColor() {
        return Color.LIGHTCORAL;
    }
}
