import com.jason.tank.Dir;
import com.jason.tank.net.MsgDecoder;
import com.jason.tank.net.MsgEncoder;
import com.jason.tank.net.MsgType;
import com.jason.tank.net.TankStartMovingMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankStartMovingMsgCodecTest {

    @Test
    public void TestEncode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 10, Dir.LEFT);

        ch.pipeline().addLast(new MsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankStartMoving, msgType);

        assertEquals(28, buf.readInt());

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];

        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.LEFT, dir);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 10, Dir.LEFT);

        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(MsgType.TankStartMoving.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());

        TankStartMovingMsg msgR = (TankStartMovingMsg) ch.readInbound();

        assertEquals(5, msgR.getX());
        assertEquals(10, msgR.getY());
        assertEquals(Dir.LEFT, msgR.getDir());
        assertEquals(id, msgR.getId());
    }
}
