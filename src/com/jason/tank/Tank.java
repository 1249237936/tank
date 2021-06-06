package com.jason.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private int speed = 1;

    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    Rectangle rect = new Rectangle();

    private boolean moving = true;

    private boolean living = true;

    private Random random = new Random();

    private TankFrame tf;

    private Group group = Group.BAD;

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        if (group.equals(Group.GOOD)) speed = 10;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public void paint(Graphics g) {

        if (!living) tf.tanks.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            default:
                break;
        }

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
        if (this.x < 0) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y < 28) x = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = 28;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        this.tf.bullets.add(new Bullet(bX, bY, dir, this.group, tf));
    }

    public void die() {
        living = false;
    }

    //getter setter
    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

//getter setter

}
