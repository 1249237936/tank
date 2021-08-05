package com.jason.tank.net;

import com.jason.tank.Bullet;
import com.jason.tank.Dir;
import com.jason.tank.Group;
import com.jason.tank.TankFrame;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.UUID;

@Setter
@Getter
public class BulletNewMsg extends Msg {

    private UUID playerID;
    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public BulletNewMsg(Bullet bullet) {
        this.playerID = TankFrame.INSTANCE.getMainTank().getId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }

    public BulletNewMsg() {
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)
        ) {
            //写主战坦克id
            dos.writeLong(this.playerID.getMostSignificantBits());
            dos.writeLong(this.playerID.getLeastSignificantBits());
            //写子弹id
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {

        try(DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))) {
            this.playerID = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
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
                .append("playerid=" + playerID + " | ")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("dir=" + dir + " | ")
                .append("group=" + group + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
