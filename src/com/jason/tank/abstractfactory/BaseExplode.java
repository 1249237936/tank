package com.jason.tank.abstractfactory;

import com.jason.tank.ResourceMgr;

import java.awt.*;

public abstract class BaseExplode {
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    public abstract void paint(Graphics g);
}
