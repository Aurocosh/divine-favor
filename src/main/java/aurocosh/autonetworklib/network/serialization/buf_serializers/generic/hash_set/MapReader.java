package aurocosh.autonetworklib.network.serialization.buf_serializers.generic.hash_set;

import aurocosh.autonetworklib.network.serialization.interfaces.BufReader;
import aurocosh.autonetworklib.network.serialization.interfaces.ContainerGenerator;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class MapReader<T extends Map<Object, Object>> implements BufReader<T> {
    private final ContainerGenerator<T> containerGenerator;

    private final BufReader keyReader;
    private final BufReader valueReader;

    public MapReader(ContainerGenerator<T> containerGenerator, BufReader keyReader, BufReader valueReader) {
        this.containerGenerator = containerGenerator;
        this.keyReader = keyReader;
        this.valueReader = valueReader;
    }

    public T read(ByteBuf buf) {
        int length = buf.readInt();
        if (length == 0)
            return containerGenerator.get(0);
        T values = containerGenerator.get(length);
        for (int i = 0; i < length; i++) {
            Object first = keyReader.read(buf);
            Object second = valueReader.read(buf);
            values.put(first, second);
        }
        return values;
    }
}
