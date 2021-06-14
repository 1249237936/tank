package com.jason.tank.strategy;

import com.jason.tank.*;
import com.jason.tank.decorator.RectDecorator;
import com.jason.tank.decorator.TailDecorator;

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
        GameModel.getInstance().add(
                new RectDecorator(
                        new TailDecorator(
                        new Bullet(bX, bY, t.getDir(), t.getGroup()))));

        if (t.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
