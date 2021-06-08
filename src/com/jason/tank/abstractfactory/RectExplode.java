package com.jason.tank.abstractfactory;

import com.jason.tank.Audio;
import com.jason.tank.TankFrame;

import java.awt.*;

public class RectExplode extends BaseExplode{
    private int x, y;

    private TankFrame tf;

    private int step = 0;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;

        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 10 * step, 10 * step);
        step++;

        if (step >= 15) tf.explodes.remove(this);

        g.setColor(c);
    }
}
