package aurocosh.autonetworklib.network.serialization.buf_serializers.generic.hash_set;

import aurocosh.autonetworklib.network.serialization.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class MapWriter<T extends Map<Object, Object>, K extends T> implements BufWriter<K> {
    private final BufWriter keyWriter;
    private final BufWriter valueWriter;

    public MapWriter(BufWriter keyWriter, BufWriter valueWriter) {
        this.keyWriter = keyWriter;
        this.valueWriter = valueWriter;
    }

    public void write(ByteBuf buf, K values) {
        buf.writeInt(values.size());
        if (values.isEmpty())
            return;
        for (Map.Entry<Object, Object> entry : values.entrySet()) {
            keyWriter.write(buf, entry.getKey());
            valueWriter.write(buf, entry.getValue());
        }
    }
}
