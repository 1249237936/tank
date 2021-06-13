package com.jason.tank.strategy;

import com.jason.tank.*;

public class FourFireStrategy implements FireStrategy {
    private FourFireStrategy() {}
    private static volatile FourFireStrategy INSTANCE = null;

    public static FourFireStrategy getInstance() {
        if (INSTANCE == null) {
            synchronized (FourFireStrategy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FourFireStrategy();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.getFourDir();
        for (Dir dir : dirs) {
            new Bullet(bX, bY, dir, t.getGroup());
        }


        if (t.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
