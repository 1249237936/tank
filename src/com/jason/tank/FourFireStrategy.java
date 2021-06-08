package com.jason.tank;

import com.jason.tank.abstractfactory.BaseTank;

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
    public void fire(BaseTank t) {
        int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.getFourDir();
        for (Dir dir : dirs) {
            //new Bullet(bX, bY, dir, t.getGroup(), t.getTf());
            t.tf.gf.createBullet(bX, bY, dir, t.group, t.tf);
        }


        if (t.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
