package com.jason.tank.strategy;

import com.jason.tank.*;

public class EightFireStrategy implements FireStrategy {
    private EightFireStrategy() {}
    private static volatile EightFireStrategy INSTANCE = null;

    public static EightFireStrategy getInstance() {
        if (INSTANCE == null) {
            synchronized (EightFireStrategy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EightFireStrategy();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(bX, bY, dir, t.getGroup());
        }


        if (t.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
