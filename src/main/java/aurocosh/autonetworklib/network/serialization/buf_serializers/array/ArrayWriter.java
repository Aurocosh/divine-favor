package aurocosh.autonetworklib.network.serialization.buf_serializers.array;

import aurocosh.autonetworklib.network.serialization.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;

public class ArrayWriter implements BufWriter<Object> {
    private final BufWriter writer;

    public ArrayWriter(BufWriter writer) {
        this.writer = writer;
    }

    public void write(ByteBuf buf, Object array) {
        int length = Array.getLength(array);
        buf.writeInt(length);
        for (int i = 0; i < length; i ++)
            writer.write(buf, Array.get(array, i));
    }
}
