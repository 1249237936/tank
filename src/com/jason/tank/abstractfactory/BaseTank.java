package com.jason.tank.abstractfactory;

import com.jason.tank.Dir;
import com.jason.tank.Group;
import com.jason.tank.ResourceMgr;
import com.jason.tank.TankFrame;

import java.awt.*;

public abstract class BaseTank {
    public TankFrame tf;
    public Group group = Group.BAD;
    public Rectangle rect = new Rectangle();
    public int x, y;
    public boolean moving = true;
    public Dir dir = Dir.DOWN;

    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    public abstract void paint(Graphics g);
    public abstract void die();
    public abstract void fire();
}
