package com.jason.tank.net;

import com.jason.tank.Dir;
import com.jason.tank.Tank;
import com.jason.tank.TankFrame;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

@Setter
@Getter
public class TankDirChangedMsg extends Msg {

    private UUID id;
    private Dir dir;
    private int x, y;

    public TankDirChangedMsg() {
    }

    public TankDirChangedMsg(Tank t) {
        this.id = t.getId();
        this.dir = t.getDir();
        this.x = t.getX();
        this.y = t.getY();
    }

    public TankDirChangedMsg(UUID id, Dir dir, int x, int y) {
        this.id = id;
        this.dir = dir;
        this.x = x;
        this.y = y;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDirChanged;
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;

        Tank t = TankFrame.INSTANCE.findByUUID(id);

        if (t != null) {
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(dir);
        }
    }

    @Override
    public void parse(byte[] bytes) {
        try (
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        ) {
            id = new UUID(dis.readLong(), dis.readLong());
            x = dis.readInt();
            y = dis.readInt();
            dir = Dir.values()[dis.readInt()];
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
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
