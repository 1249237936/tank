package com.jason.tank;

public class DefaultFireStrategy implements FireStrategy {
    private DefaultFireStrategy() {}

    private static class DefaultFireStrategyHolder {
        private static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }
    public static DefaultFireStrategy getInstance() {
        return DefaultFireStrategyHolder.INSTANCE;
    }

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        new Bullet(bX, bY, t.getDir(), t.getGroup(), t.getTf());

        if (t.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
