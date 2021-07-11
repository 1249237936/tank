package com.jason.tank;

import com.jason.tank.cor.ColliderChain;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private static volatile GameModel INSTANCE = new GameModel();

    public static GameModel getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE.init();
    }

    private GameModel() {
    }

    private void init() {
        int initTankCount = PropertyMgr.getInt("initTankCount");

        //敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD));
        }

        //初始化墙
        new Wall(150, 150, 200, 50);
        new Wall(550, 150, 200, 50);
        new Wall(300, 300, 50, 200);
        new Wall(550, 300, 50, 200);

        myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);
    }

    Tank myTank;
    //java.util.List<Bullet> bullets = new ArrayList<>();
    //java.util.List<Tank> tanks = new ArrayList<>();
    //List<Explode> explodes = new ArrayList<>();

    private List<GameObject> objects = new ArrayList<>();


    ColliderChain chain = ColliderChain.getInstance();

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        //g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        //g.drawString("敌人的数量：" + tanks.size(), 10, 80);
        //g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        /*collision detect*/
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                chain.collide(o1, o2);
            }
        }

    }

    public Tank getMainTank() {
        return myTank;
    }

    public void save() {
        File f = new File("j:/tank.data");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(myTank);
            oos.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File f = new File("j:/tank.data");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            myTank = (Tank) ois.readObject();
            objects = (List<GameObject>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
