package com.jason.tank.abstractfactory;

import com.jason.tank.Dir;
import com.jason.tank.Group;
import com.jason.tank.TankFrame;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
}
