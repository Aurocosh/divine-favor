package aurocosh.autonetworklib.network.serialization.buf_serializers.generic.array_list;

import aurocosh.autonetworklib.network.serialization.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public class CollectionListWriter<T extends Collection<Object>> implements BufWriter<T> {
    private final BufWriter writer;

    public CollectionListWriter(BufWriter writer) {
        this.writer = writer;
    }

    public void write(ByteBuf buf, T values) {
        buf.writeInt(values.size());
        if(values.isEmpty())
            return;
        for (Object value : values)
            writer.write(buf, value);
    }
}
