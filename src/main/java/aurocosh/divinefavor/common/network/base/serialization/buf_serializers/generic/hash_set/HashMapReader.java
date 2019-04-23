package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;

public class HashMapReader implements BufReader<HashMap> {
    private final BufReader keyReader;
    private final BufReader valueReader;

    public HashMapReader(BufReader keyReader, BufReader valueReader) {
        this.keyReader = keyReader;
        this.valueReader = valueReader;
    }

    public HashMap<Object, Object> read(ByteBuf buf) {
        int length = buf.readInt();
        if (length == 0)
            return new HashMap<>();
        HashMap<Object, Object> values = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            Object first = keyReader.read(buf);
            Object second = valueReader.read(buf);
            values.put(first, second);
        }
        return values;
    }
}
