package com.jason.tank.cor;

import com.jason.tank.*;

public class BulletWallCollider implements Collider{
    private BulletWallCollider() {}
    private static class BulletWallColliderHolder {
        private static final BulletWallCollider instance = new BulletWallCollider();
    }
    public static BulletWallCollider getInstance() {
        return BulletWallColliderHolder.instance;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet b = (Bullet) o1;
            Wall w = (Wall) o2;


            if (b.rect.intersects(w.rect)) b.die();
        } else if (o1 instanceof Wall && o2 instanceof Bullet) {
            return collide(o2, o1);
        }

        return true;
    }
}
