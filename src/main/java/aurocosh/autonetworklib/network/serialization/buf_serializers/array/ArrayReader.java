package aurocosh.autonetworklib.network.serialization.buf_serializers.array;

import aurocosh.autonetworklib.network.serialization.interfaces.BufReader;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;

public class ArrayReader implements BufReader<Object> {
    private final Class componentClazz;
    private final BufReader reader;

    public ArrayReader(Class componentClazz, BufReader reader) {
        this.componentClazz = componentClazz;
        this.reader = reader;
    }

    public Object read(ByteBuf buf) {
        int length = buf.readInt();
        Object array = Array.newInstance(componentClazz, length);
        if (length == 0)
            return array;

        for (int i = 0; i < length; i++)
            Array.set(array, i, reader.read(buf));
        return array;
    }
}
