package com.jason.tank;

import com.jason.tank.net.BulletNewMsg;
import com.jason.tank.net.Client;
import com.jason.tank.net.TankDieMsg;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;

@Getter @Setter
public class Bullet {
    private static final int SPEED = PropertyMgr.getInt("bulletSpeed");
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    private Dir dir;

    private UUID id = UUID.randomUUID();
    private UUID playerId;

    Rectangle rect = new Rectangle();

    private boolean living = true;

    private TankFrame tf;

    private Group group = Group.BAD;

    public Bullet(UUID playerId,int x, int y, Dir dir, Group group, TankFrame tf) {
        this.playerId = playerId;
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
        Client.INSTANCE.send(new BulletNewMsg(this));
    }

    public void collideWith(Tank tank) {
        if (this.playerId.equals(tank.getId())) return;
//        Rectangle rect1 = new Rectangle(x, y, WIDTH, HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (this.living && tank.isLiving() && rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            Client.INSTANCE.send(new TankDieMsg(this.playerId, tank.getId()));
        }
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



    private void die() {
        living = false;
    }


}
