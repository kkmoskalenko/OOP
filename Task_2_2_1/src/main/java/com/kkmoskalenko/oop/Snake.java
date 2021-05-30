package com.kkmoskalenko.oop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Snake {
    private final List<Joint> joints = new ArrayList<>();
    private Direction direction = Direction.RIGHT;

    // Constants
    static final int INITIAL_LENGTH = 3;
    private static final int JOINTS_WITH_NO_COLLISION = 3;

    Snake() {
        Random random = new Random();

        int initialX = random.nextInt(
                Board.WIDTH - INITIAL_LENGTH
        ) + INITIAL_LENGTH;
        int initialY = random.nextInt(Board.HEIGHT);

        for (int i = 0; i < INITIAL_LENGTH; i++) {
            joints.add(new Joint(initialX - i, initialY));
        }
    }

    protected Color getHeadColor() {
        return Color.DARKGREEN;
    }

    protected Color getBodyColor() {
        return Color.FORESTGREEN;
    }

    protected Joint getHead() {
        return joints.get(0);
    }

    void move() {
        for (int i = joints.size() - 1; i > 0; i--) {
            Joint curr = joints.get(i);
            Joint prev = joints.get(i - 1);

            curr.x = prev.x;
            curr.y = prev.y;
        }

        Joint head = getHead();

        switch (direction) {
            case UP -> {
                if (head.y == 0) {
                    head.y = Board.HEIGHT;
                }
                head.y--;
            }
            case DOWN -> {
                if (head.y == Board.HEIGHT - 1) {
                    head.y = -1;
                }
                head.y++;
            }
            case LEFT -> {
                if (head.x == 0) {
                    head.x = Board.WIDTH;
                }
                head.x--;
            }
            case RIGHT -> {
                if (head.x == Board.WIDTH - 1) {
                    head.x = -1;
                }
                head.x++;
            }
        }
    }

    void grow() {
        joints.add(new Joint(-1, -1));
    }

    boolean checkCollision() {
        for (int i = joints.size() - 1; i > JOINTS_WITH_NO_COLLISION; i--) {
            Joint head = getHead();
            Joint joint = joints.get(i);

            if (head.x == joint.x && head.y == joint.y) {
                return true;
            }
        }

        return false;
    }

    boolean checkCollision(final Apple apple) {
        Joint head = getHead();
        return head.x == apple.getX()
                && head.y == apple.getY();
    }

    /**
     * Shortens the snake if its tail is bitten off
     *
     * @param otherSnakes List of snakes to check for collision.
     *                    May include the snake itself.
     * @return Whether the snake is dead after shortening
     */
    boolean handleBite(final Snake[] otherSnakes) {
        for (Snake snake : otherSnakes) {
            if (snake == this) {
                continue;
            }

            for (int i = 0; i < joints.size(); i++) {
                Joint anotherHead = snake.getHead();
                Joint joint = joints.get(i);

                if (anotherHead.x == joint.x && anotherHead.y == joint.y) {
                    if (i == 0) {
                        return true;
                    } else {
                        joints.subList(i, joints.size()).clear();
                        return false;
                    }
                }
            }
        }

        return false;
    }

    void changeDirection(final Direction newDirection) {
        switch (newDirection) {
            case UP -> {
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
            }
            case DOWN -> {
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
            }
            case LEFT -> {
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
            }
            case RIGHT -> {
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
            }
        }
    }

    void draw(final GraphicsContext gc) {
        for (int i = 0; i < joints.size(); i++) {
            Joint joint = joints.get(i);
            int size = Board.CELL_SIZE;

            gc.setFill(i == 0 ? getHeadColor() : getBodyColor());
            gc.fillRect(joint.x * size, joint.y * size, size, size);
        }
    }

    int getLength() {
        return joints.size();
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected static class Joint {
        private int x;
        private int y;

        Joint(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        protected int getX() {
            return x;
        }

        protected int getY() {
            return y;
        }
    }
}
