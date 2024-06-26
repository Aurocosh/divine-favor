package aurocosh.autonetworklib.network.serialization.class_serializers;

import aurocosh.autonetworklib.network.serialization.interfaces.BufReader;
import aurocosh.autonetworklib.network.serialization.interfaces.BufWriter;
import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider;
import aurocosh.autonetworklib.util.UtilReflection;
import aurocosh.divinefavor.DivineFavor;
import io.netty.buffer.ByteBuf;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClassBufSerializer {
    private final Class clazz;
    private final FieldBufSerializer[] fieldBufSerializers;

    public ClassBufSerializer(Class clazz) {
        this.clazz = clazz;
        Field[] fields = UtilReflection.getMutableFields(clazz);
        Type[] fieldTypes = getFieldTypes(fields);
        BufWriter[] writers = BufSerializerProvider.getWriters(fieldTypes);
        BufReader[] readers = BufSerializerProvider.getReaders(fieldTypes);

        List<FieldBufSerializer> fieldBufSerializerList = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            BufWriter writer = writers[i];
            if (writer == null) {
                DivineFavor.logger.error("Can't find buf writer for for field {} in class {}", field.getName(), clazz.getName());
                continue;
            }
            BufReader reader = readers[i];
            if (reader == null) {
                DivineFavor.logger.error("Can't find buf reader for for field {} in class {}", field.getName(), clazz.getName());
                continue;
            }

            boolean accessible = field.isAccessible();
            if(!accessible)
                field.setAccessible(true);

            MethodHandle getter = UtilReflection.unreflectGetter(field);
            MethodHandle setter = UtilReflection.unreflectSetter(field);

            if(!accessible)
                field.setAccessible(false);

            if (getter != null && setter != null)
                fieldBufSerializerList.add(new FieldBufSerializer(field, setter, reader, getter, writer));
        }

        fieldBufSerializers = fieldBufSerializerList.toArray(new FieldBufSerializer[0]);
    }

    private Type[] getFieldTypes(Field[] fields) {
        Type[] types = new Type[fields.length];
        for (int i = 0; i < fields.length; i++)
            types[i] = fields[i].getGenericType();
        return types;
    }

    public final void fromBytes(Object object, ByteBuf buf) {
        try {
            for (FieldBufSerializer serializer : fieldBufSerializers)
                serializer.deserialize(object, buf);
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Field deserialization error in class " + clazz.getName(), throwable);
        }
    }

    public final void toBytes(Object object, ByteBuf buf) {
        try {
            for (FieldBufSerializer serializer : fieldBufSerializers)
                serializer.serialize(object, buf);
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Field serialization error in class " + clazz.getName(), throwable);
        }
    }
}
