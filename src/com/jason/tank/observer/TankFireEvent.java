package com.jason.tank.observer;

import com.jason.tank.Tank;

public class TankFireEvent {
    private Tank tank;

    public Tank getSource() {
        return tank;
    }

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }
}
