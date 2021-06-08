package com.jason.tank.abstractfactory;

import com.jason.tank.Dir;
import com.jason.tank.Group;
import com.jason.tank.TankFrame;

public class RectFactory extends GameFactory {
    private RectFactory() {
    }
    private static volatile RectFactory INSTANCE;
    public static RectFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (RectFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RectFactory();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectTank(x, y, dir, group, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x, y, tf);
    }
}
