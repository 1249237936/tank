package com.jason.tank.net;

import com.jason.tank.Tank;
import com.jason.tank.TankFrame;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.UUID;

@Setter
@Getter
public class TankStopMsg extends Msg {

    private UUID id;

    private int x, y;

    public TankStopMsg(Tank t) {
        this.id = t.getId();
        this.x = t.getX();
        this.y = t.getY();
    }

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMsg() {
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)
        ) {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
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
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))
        ) {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {
        //if this msg is send by myself do nothing
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;

        Tank t = TankFrame.INSTANCE.findByUUID(id);

        if (t != null) {
            t.setMoving(false);
            t.setX(x);
            t.setY(y);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
