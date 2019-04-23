package aurocosh.divinefavor.common.network.base.serialization.generic.array_list;

import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

public class ArrayListWriter implements BufWriter<ArrayList> {
    private final BufWriter writer;

    public ArrayListWriter(BufWriter writer) {
        this.writer = writer;
    }

    public void write(ByteBuf buf, ArrayList values) {
        buf.writeInt(values.size());
        if(values.isEmpty())
            return;
        for (Object value : values)
            //noinspection unchecked
            writer.write(buf, value);
    }
}
