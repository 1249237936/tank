package com.jason.tank;

public enum Dir {
    LEFT,
    UP,
    RIGHT,
    DOWN,
    LEFT_UP,
    RIGHT_UP,
    DOWN_RIGHT,
    LEFT_DOWN,
    ;
    public static Dir[] getFourDir() {
        return new Dir[]{UP, DOWN, LEFT, RIGHT};
    }
}
