package com.jason.tank;

import com.jason.tank.abstractfactory.BaseTank;

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
    public void fire(BaseTank t) {
        int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            //new Bullet(bX, bY, dir, t.getGroup(), t.getTf());
            t.tf.gf.createBullet(bX, bY, dir, t.group, t.tf);
        }


        if (t.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
