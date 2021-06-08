package com.jason.tank;

import com.jason.tank.abstractfactory.BaseBullet;
import com.jason.tank.abstractfactory.BaseExplode;
import com.jason.tank.abstractfactory.BaseTank;
import com.jason.tank.abstractfactory.GameFactory;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.tank.ReflectUtil.getSingleInstance;

public class TankFrame extends Frame {
    public static final String GAME_FACTORY = "GameFactory";
    //GameFactory gf = RectFactory.getInstance();
    public GameFactory gf = getSingleInstance(GAME_FACTORY, GameFactory.class);

    //Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
    BaseTank myTank = gf.createTank(200, 400, Dir.DOWN, Group.GOOD, this);

    public List<BaseBullet> bullets = new ArrayList<>();
    public List<BaseTank> tanks = new CopyOnWriteArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();

    public static final int GAME_WIDTH = PropertyMgr.getInt("gameWidth"), GAME_HEIGHT = PropertyMgr.getInt("gameHeight");

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setVisible(true);
        setTitle("tank war");

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("敌人的数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        /*collision detect*/
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

    }

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;

                default:
                    break;
            }

            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;

                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;

                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) myTank.moving = false;
            else {
                myTank.moving = true;

                if (bL) myTank.dir = Dir.LEFT;
                if (bU) myTank.dir = Dir.UP;
                if (bR) myTank.dir = Dir.RIGHT;
                if (bD) myTank.dir = Dir.DOWN;
            }

        }

    }

}
