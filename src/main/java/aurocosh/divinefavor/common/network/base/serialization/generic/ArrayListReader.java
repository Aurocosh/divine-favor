package aurocosh.divinefavor.common.network.base.serialization.generic;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

public class ArrayListReader {
    private final BufReader<Object> reader;

    public ArrayListReader(BufReader<Object> reader) {
        this.reader = reader;
    }

    public ArrayList<Object> read(ByteBuf buf) {
        int length = buf.readInt();
        if (length == 0)
            return new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
            values.add(reader.read(buf));
        return values;
    }
}
