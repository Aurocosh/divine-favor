package aurocosh.divinefavor.common.network.base.serialization.class_serializers;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.util.UtilReflection;
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
        BufWriter[] writers = TypeBufSerializerProvider.getWriters(fieldTypes);
        BufReader[] readers = TypeBufSerializerProvider.getReaders(fieldTypes);

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

            MethodHandle getter = UtilReflection.unreflectGetter(field);
            if (getter == null)
                continue;

            MethodHandle setter = UtilReflection.unreflectSetter(field);
            if (setter == null)
                continue;

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
