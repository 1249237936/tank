package com.jason.tank.net;

import com.jason.tank.Dir;
import com.jason.tank.Tank;
import com.jason.tank.TankFrame;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.UUID;

@Getter
@Setter
public class TankStartMovingMsg extends Msg {

    private UUID id;

    private int x, y;

    private Dir dir;

    public TankStartMovingMsg() {
    }

    public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public TankStartMovingMsg(Tank tank) {
        this.id = tank.getId();
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    @Override
    public void handle() {
        if (id.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;

        Tank t = TankFrame.INSTANCE.findByUUID(id);

        if (t != null) {
            t.setMoving(true);
            t.setX(x);
            t.setY(y);
            t.setDir(dir);
        }
    }

    @Override
    public void parse(byte[] bytes) {

        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))) {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("dir=" + dir + " | ")
                .append("]");
        return builder.toString();
    }
}
