package com.jason.tank.cor;

import com.jason.tank.GameObject;
import com.jason.tank.Tank;

public class TankTankCollider implements Collider{
    private TankTankCollider() {}
    private static class BulletTankColliderHolder {
        private static final TankTankCollider instance = new TankTankCollider();
    }
    public static TankTankCollider getInstance() {
        return BulletTankColliderHolder.instance;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;

            if (t1.getRect().intersects(t2.getRect())) {
                t1.back();
                t2.back();
            }
        }
        return true;
    }
}
