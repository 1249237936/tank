package com.jason.tank.observer;

public class TankHandle implements TankFireObserver {

    @Override
    public void actionOnFile(TankFireEvent event) {
        event.getSource().fire();
    }
}
