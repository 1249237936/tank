package com.jason.tank.abstractfactory;

import com.jason.tank.*;

public class DefaultFactory extends GameFactory {
    private DefaultFactory() {}
    private static DefaultFactory INSTANCE = null;

    public static DefaultFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (DefaultFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DefaultFactory();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank(x, y, dir, group, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new Explode(x, y, tf);
    }
}
