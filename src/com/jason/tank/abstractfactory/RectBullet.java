package com.jason.tank.abstractfactory;

import com.jason.tank.*;

import java.awt.*;

public class RectBullet extends BaseBullet {
    private static final int SPEED = PropertyMgr.getInt("bulletSpeed");
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    private Dir dir;

    Rectangle rect = new Rectangle();

    private boolean living = true;

    private final TankFrame tf;

    private Group group;

    public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
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

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20, 20);
        g.setColor(c);

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
