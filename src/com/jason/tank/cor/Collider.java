package com.jason.tank.cor;

import com.jason.tank.GameObject;

public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
