package com.jason.tank;

import com.jason.tank.abstractfactory.BaseExplode;

import java.awt.*;

public class Explode extends BaseExplode {

    private int x, y;
    private TankFrame tf;

    private int step = 0;

    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;

        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);

        if (step >= ResourceMgr.explodes.length) tf.explodes.remove(this);
    }


}
