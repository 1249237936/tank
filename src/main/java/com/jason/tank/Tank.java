package com.jason.tank;

import com.jason.tank.net.Client;
import com.jason.tank.net.TankJoinMsg;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

public class Tank {
    private static final String GOOD_FS= "goodFS";
    private static final String BAD_FS= "badFS";
    private static final String GET_INSTANCE = "getInstance";
    private static final String TANK_SPEED = "tankSpeed";
    private static final String HIGH_SPEED = "HighSpeed";


    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    @Getter
    @Setter
    private UUID id = UUID.randomUUID();

    private int x, y;
    private Dir dir = Dir.DOWN;
    private int speed = PropertyMgr.getInt(HIGH_SPEED);

    Rectangle rect = new Rectangle();

    private boolean moving = false;

    private boolean living = true;

    private Random random = new Random();

    private TankFrame tf;

    private Group group = Group.BAD;

    private FireStrategy fs;

    public Tank(TankJoinMsg msg) {
        this.x = msg.x;
        this.y = msg.y;
        this.dir = msg.dir;
        this.moving = msg.moving;
        this.group = msg.group;
        this.id = msg.id;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf)  {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
//        if (group.equals(Group.GOOD)) speed = PropertyMgr.getInt(HIGH_SPEED);
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        if (this.group == Group.GOOD) fs = getFireStrategyByName(GOOD_FS);
        else fs = getFireStrategyByName(BAD_FS);
    }

    private FireStrategy getFireStrategyByName(String name) {
        try {
            Class fsClazz = Class.forName(PropertyMgr.getString(name));
            Method method = fsClazz.getDeclaredMethod(GET_INSTANCE);
            return (FireStrategy) method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DefaultFireStrategy.getInstance();
    }

    public void paint(Graphics g) {

        if (!living) tf.tanks.remove(this);
        //uuid on head
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), this.x, this.y - 10);
        g.setColor(c);

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
        fs.fire(this);
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

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }
//getter setter

}
