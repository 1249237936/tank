package com.jason.tank.net;

public abstract class Msg {
    public abstract void handle();
    public abstract byte[] toBytes();
}
