package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.array_list;

import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.ContainerGenerator;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public class CollectionListReader<T extends Collection<Object>> implements BufReader<T> {
    private final ContainerGenerator<T> containerGenerator;
    private final BufReader reader;

    public CollectionListReader(ContainerGenerator<T> containerGenerator, BufReader reader) {
        this.reader = reader;
        this.containerGenerator = containerGenerator;
    }

    public T read(ByteBuf buf) {
        int length = buf.readInt();
        if (length == 0)
            return containerGenerator.get(0);
        T values = containerGenerator.get(length);
        for (int i = 0; i < length; i++)
            values.add(reader.read(buf));
        return values;
    }
}
