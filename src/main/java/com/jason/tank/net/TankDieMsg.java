package com.jason.tank.net;

import com.jason.tank.Tank;
import com.jason.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg {
    private UUID bulletId;//who killed me
    private UUID id;

    public TankDieMsg(UUID playerId, UUID id) {
        this.bulletId = playerId;
        this.id = id;
    }

    public TankDieMsg() {}

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
        ) {
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
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
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {
        System.out.println("we got a tank dir: " + id);
        System.out.println("and my tank is: " + TankFrame.INSTANCE.getMainTank().getId());
        Tank tt = TankFrame.INSTANCE.findByUUID(id);
        System.out.println("i found a tank with this id: " + tt);

        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) {
            TankFrame.INSTANCE.getMainTank().die();
        } else {

            Tank t = TankFrame.INSTANCE.findByUUID(id);
            if (t != null) {
                t.die();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("bulletId=" + bulletId + "|")
                .append("id=" + id + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
