package aurocosh.autonetworklib.network.serialization.buf_serializers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;

public class Vec3dSerializer {
    public static void writeVec3d(ByteBuf buf, Vec3d value) {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }

    public static Vec3d readVec3d(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new Vec3d(x, y, z);
    }
}
