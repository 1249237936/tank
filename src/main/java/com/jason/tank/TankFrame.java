package com.jason.tank;

import com.jason.tank.net.Client;
import com.jason.tank.net.TankDirChangedMsg;
import com.jason.tank.net.TankStartMovingMsg;
import com.jason.tank.net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();

    Random r = new Random();

//    public static final int GAME_WIDTH = PropertyMgr.getInt("gameWidth"), GAME_HEIGHT = PropertyMgr.getInt("gameHeight");
    Tank myTank = new Tank(r.nextInt(GAME_WIDTH), r.nextInt(GAME_HEIGHT), Dir.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    Map<UUID, Tank> tanks = new HashMap<>();
    List<Explode> explodes = new ArrayList<>();

    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;

    public void addBullect(Bullet b) {
        bullets.add(b);
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }

    public Tank findByUUID(UUID id) {
        return tanks.get(id);
    }

    private TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        //setVisible(true);
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
        g.drawString("bullets：" + bullets.size(), 10, 60);
        g.drawString("tanks：" + tanks.size(), 10, 80);
        g.drawString("explodes：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

		//java8 stream api
		tanks.values().stream().forEach((e)->e.paint(g));

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        /*collision detect*/
        Collection<Tank> values = tanks.values();
        for (int i = 0; i < bullets.size(); i++) {
            for (Tank t : values)
                bullets.get(i).collideWith(t);

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
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    setMainTankDir();
                    break;

                default:
                    break;
            }


			new Thread(()->new Audio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    setMainTankDir();
                    break;

                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;

                default:
                    break;
            }

        }

        private void setMainTankDir() {
            //save the old dir
            Dir dir = myTank.getDir();

            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMainTank()));
            } else {

                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
                //发出坦克移动的消息
                if (!myTank.isMoving())
                    Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));

                myTank.setMoving(true);

                if (dir != myTank.getDir()) {
                    Client.INSTANCE.send(new TankDirChangedMsg(myTank));
                }
            }

        }

    }

    public Tank getMainTank() {
        return myTank;
    }
}
