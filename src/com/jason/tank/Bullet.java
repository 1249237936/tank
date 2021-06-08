package com.jason.tank;

import com.jason.tank.abstractfactory.BaseBullet;
import com.jason.tank.abstractfactory.BaseExplode;
import com.jason.tank.abstractfactory.BaseTank;

import java.awt.*;

public class Bullet extends BaseBullet {
    private static final int SPEED = PropertyMgr.getInt("bulletSpeed");
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    private Dir dir;

    Rectangle rect = new Rectangle();

    private boolean living = true;

    private final TankFrame tf;

    private Group group;

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        tf.bullets.add(this);
    }

    public void paint(Graphics g) {

        if (!living) {
            tf.bullets.remove(this);
        }

        /*Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(color);*/

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;

            case LEFT_UP:
                g.drawImage(ResourceMgr.bulletLU, x, y, null);
                break;
            case RIGHT_UP:
                g.drawImage(ResourceMgr.bulletRU, x, y, null);
                break;
            case LEFT_DOWN:
                g.drawImage(ResourceMgr.bulletLD, x, y, null);
                break;
            case DOWN_RIGHT:
                g.drawImage(ResourceMgr.bulletRD, x, y, null);
                break;

            default:
                break;
        }

        move();
    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;

            case LEFT_UP:
                x -= SPEED;
                y -= SPEED;
                break;
            case RIGHT_UP:
                y -= SPEED;
                x += SPEED;
                break;
            case LEFT_DOWN:
                x -= SPEED;
                y += SPEED;
                break;
            case DOWN_RIGHT:
                y += SPEED;
                x += SPEED;
                break;

            default:
                break;
        }
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    public void collideWith(BaseTank tank) {
        if (this.group == tank.group) return;

        //Rectangle rect1 = new Rectangle(x, y, WIDTH, HEIGHT);
        //Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int eX = tank.x + BaseTank.WIDTH / 2 - BaseExplode.WIDTH / 2;
            int eY = tank.y + BaseTank.HEIGHT / 2 - BaseExplode.HEIGHT / 2;
            //tf.explodes.add(new Explode(eX, eY, tf));
            tf.explodes.add(tf.gf.createExplode(eX, eY, tf));
        }
    }

    private void die() {
        living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
