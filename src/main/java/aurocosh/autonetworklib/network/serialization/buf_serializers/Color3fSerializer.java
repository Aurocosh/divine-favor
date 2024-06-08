package aurocosh.autonetworklib.network.serialization.buf_serializers;

import io.netty.buffer.ByteBuf;

import javax.vecmath.Color3f;

public class Color3fSerializer {
    public static void writeColor3f(ByteBuf buf, Color3f value) {
        buf.writeFloat(value.x);
        buf.writeFloat(value.y);
        buf.writeFloat(value.z);
    }

    public static Color3f readColor3f(ByteBuf buf) {
        float x = buf.readFloat();
        float y = buf.readFloat();
        float z = buf.readFloat();
        return new Color3f(x, y, z);
    }
}
