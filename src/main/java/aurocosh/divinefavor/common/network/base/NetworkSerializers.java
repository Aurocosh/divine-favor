package aurocosh.divinefavor.common.network.base;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;

import javax.vecmath.Color3f;

public class NetworkSerializers {


    static void writeColor3f(ByteBuf buf, Color3f value) {
        buf.writeFloat(value.x);
        buf.writeFloat(value.y);
        buf.writeFloat(value.z);
    }

    static Color3f readColor3f(ByteBuf buf) {
        float x = buf.readFloat();
        float y = buf.readFloat();
        float z = buf.readFloat();
        return new Color3f(x, y, z);
    }

    static void writeVec3d(ByteBuf buf, Vec3d value) {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }

    static Vec3d readVec3d(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new Vec3d(x, y, z);
    }

    static int[] readIntArray(ByteBuf buf) {
        int length = buf.readInt();
        int[] values = new int[length];
        for (int i = 0; i < length; i++)
            values[i] = buf.readInt();
        return values;
    }

    static void writeIntArray(ByteBuf buf, int[] values) {
        buf.writeInt(values.length);
        for (int value : values)
            buf.writeInt(value);
    }
}
