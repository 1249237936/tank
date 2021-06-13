package com.jason.tank.cor;

import com.jason.tank.*;

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

            if (b.getGroup() == t.getGroup()) return true;

            if (b.rect.intersects(t.rect)) {
                t.die();
                b.die();
                int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
                int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
                new Explode(eX, eY);
                return false;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        }

        return true;
    }
}
