package aurocosh.divinefavor.common.network.base.serialization.generic;

import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

public class ArrayListWriter {
    private final BufWriter<Object> writer;

    public ArrayListWriter(BufWriter<Object> writer) {
        this.writer = writer;
    }

    public void write(ByteBuf buf, ArrayList values) {
        buf.writeInt(values.size());
        if(values.isEmpty())
            return;
        for (Object value : values)
            writer.write(buf, value);
    }
}
