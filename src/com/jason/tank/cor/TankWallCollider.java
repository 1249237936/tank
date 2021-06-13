package com.jason.tank.cor;

import com.jason.tank.GameObject;
import com.jason.tank.Group;
import com.jason.tank.Tank;
import com.jason.tank.Wall;

public class TankWallCollider implements Collider{
    private TankWallCollider() {}
    private static class TankWallColliderHolder {
        private static final TankWallCollider instance = new TankWallCollider();
    }
    public static TankWallCollider getInstance() {
        return TankWallColliderHolder.instance;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;

            if (t.rect.intersects(w.rect)) t.back();


        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            return collide(o2, o1);
        }

        return true;
    }
}
