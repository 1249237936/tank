package com.jason.tank.abstractfactory;

import com.jason.tank.*;

import java.awt.*;
import java.util.Random;

import static com.jason.tank.ReflectUtil.getSingleInstance;

public class RectTank extends BaseTank {
    private static final String GOOD_FS= "goodFS";
    private static final String BAD_FS= "badFS";
    private static final String TANK_SPEED = "tankSpeed";
    private static final String HIGH_SPEED = "HighSpeed";

    private int speed = PropertyMgr.getInt(TANK_SPEED);

    public boolean living = true;


    private final Random random = new Random();


    private final FireStrategy fs;

    public RectTank(int x, int y, Dir dir, Group group, TankFrame tf)  {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        if (group.equals(Group.GOOD)) speed = PropertyMgr.getInt(HIGH_SPEED);
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        if (this.group == Group.GOOD) fs = getSingleInstance(GOOD_FS, FireStrategy.class);
        else fs = getSingleInstance(BAD_FS, FireStrategy.class);
    }

    @Override
    public void paint(Graphics g) {

        if (!living) tf.tanks.remove(this);

        Color c = g.getColor();
        g.setColor(group == Group.GOOD ? Color.WHITE : Color.BLUE);
        g.fillRect(x, y, 40, 40);
        g.setColor(c);

        move();
    }

    private void move() {
        if (!moving) return;

        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case DOWN:
                y += speed;
                break;
            default:
                break;
        }

        if (random.nextInt(100) > 95 && this.group == Group.BAD) fire();

        if (this.group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        boundCheck();

        //update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundCheck() {
        if (this.x < 0) x = TankFrame.GAME_WIDTH - RectTank.WIDTH;
        if (this.y < 28) x = TankFrame.GAME_HEIGHT - RectTank.HEIGHT;
        if (this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH) x = 2;
        if (this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT) y = 28;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }
    @Override
    public void fire() {
        fs.fire(this);
    }
    @Override
    public void die() {
        living = false;
    }


}
