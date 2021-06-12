package com.jason.tank.cor;

import com.jason.tank.Bullet;
import com.jason.tank.GameObject;
import com.jason.tank.Tank;

public class BulletTankCollider implements Collider{
    private BulletTankCollider () {}
    private static class BulletTankColliderHolder {
        private static final BulletTankCollider instance = new BulletTankCollider();
    }
    public static BulletTankCollider getInstance() {
        return BulletTankColliderHolder.instance;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;
            //TODO copy code with method collidewith
            if (b.collideWith(t)) {
                return false;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        }

        return true;
    }
}
