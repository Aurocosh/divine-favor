package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set;

import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class HashMapWriter implements BufWriter<HashMap> {
    private final BufWriter keyWriter;
    private final BufWriter valueWriter;

    public HashMapWriter(BufWriter keyWriter, BufWriter valueWriter) {
        this.keyWriter = keyWriter;
        this.valueWriter = valueWriter;
    }

    public void write(ByteBuf buf, HashMap values) {
        buf.writeInt(values.size());
        if (values.isEmpty())
            return;
        HashMap<Object, Object> objectMap = (HashMap<Object, Object>)values;
        for (Map.Entry<Object, Object> entry : objectMap.entrySet()) {
            keyWriter.write(buf, entry.getKey());
            valueWriter.write(buf, entry.getValue());
        }
    }
}
