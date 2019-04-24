package aurocosh.divinefavor.common.network.base.serialization.class_serializers;

import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

public class FieldBufSerializer {
    private final Field field;

    private final MethodHandle setter;
    private final BufReader reader;

    private final MethodHandle getter;
    private final BufWriter writer;

    public FieldBufSerializer(Field field, MethodHandle setter, BufReader reader, MethodHandle getter, BufWriter writer) {
        this.field = field;
        this.setter = setter;
        this.reader = reader;
        this.getter = getter;
        this.writer = writer;
    }

    public Field getField() {
        return field;
    }

    public void deserialize(Object object, ByteBuf buf) throws Throwable {
        setter.invoke(object, reader.read(buf));
    }

    public void serialize(Object object, ByteBuf buf) throws Throwable {
        writer.write(buf, getter.invoke(object));
    }
}
