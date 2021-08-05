package com.jason.tank.net;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg {

    private UUID id;

    public TankDieMsg(UUID id) {
        this.id = id;
    }

    public TankDieMsg() {
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
        ) {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        try (
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        ) {
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
