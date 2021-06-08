package com.jason.tank;

import com.jason.tank.abstractfactory.BaseTank;

public class DefaultFireStrategy implements FireStrategy {
    private DefaultFireStrategy() {}

    private static class DefaultFireStrategyHolder {
        private static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }
    public static DefaultFireStrategy getInstance() {
        return DefaultFireStrategyHolder.INSTANCE;
    }

    public void fire(BaseTank t) {
        int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        //new Bullet(bX, bY, t.getDir(), t.getGroup(), t.getTf());
        t.tf.gf.createBullet(bX, bY, t.dir, t.group, t.tf);

        if (t.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
