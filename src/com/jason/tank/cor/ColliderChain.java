package com.jason.tank.cor;

import com.jason.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider{
    private List<Collider> colliders = new LinkedList<>();
    public void add(Collider c) {
        colliders.add(c);
    }

    private ColliderChain() {
        add(TankTankCollider.getInstance());
        add(BulletTankCollider.getInstance());
    }
    private static volatile ColliderChain instance;
    public static ColliderChain getInstance() {
        if (instance == null) {
            synchronized (ColliderChain.class) {
                if (instance == null) {
                    instance = new ColliderChain();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
