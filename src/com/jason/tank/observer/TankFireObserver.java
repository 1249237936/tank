package com.jason.tank.observer;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {
    void actionOnFile(TankFireEvent event);
}
