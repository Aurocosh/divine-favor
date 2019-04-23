package aurocosh.divinefavor.common.network.base.serialization;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.serializers.FieldSerializer;
import io.netty.buffer.ByteBuf;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassByteBufSerializer {
    private final Class clazz;
    private final FieldSerializer[] fieldSerializers;

    public ClassByteBufSerializer(Class clazz) {
        this.clazz = clazz;
        Field[] fields = getMutableFields(clazz);
        BufWriter[] writers = FieldByteBufSerializers.getFieldWriters(fields);
        BufReader[] readers = FieldByteBufSerializers.getFieldReaders(fields);

        List<FieldSerializer> fieldSerializerList = new ArrayList<>();

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

            MethodHandle getter = unreflectGetter(field);
            if (getter == null)
                continue;

            MethodHandle setter = unreflectSetter(field);
            if (setter == null)
                continue;

            fieldSerializerList.add(new FieldSerializer(field, setter, reader, getter, writer));
        }

        fieldSerializers = fieldSerializerList.toArray(new FieldSerializer[0]);
    }

    public final void fromBytes(Object object, ByteBuf buf) {
        try {
            for (FieldSerializer serializer : fieldSerializers)
                serializer.deserialize(object, buf);
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Field deserialization error in class " + clazz.getName(), throwable);
        }
    }

    public final void toBytes(Object object, ByteBuf buf) {
        try {
            for (FieldSerializer serializer : fieldSerializers)
                serializer.serialize(object, buf);
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Field serialization error in class " + clazz.getName(), throwable);
        }
    }

    private static Field[] getMutableFields(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> mutableFields = new ArrayList<>();
        for (Field field : fields)
            if (isFieldMutable(field))
                mutableFields.add(field);
        mutableFields.sort(Comparator.comparing(Field::getName));

        Field[] mutableArray = new Field[mutableFields.size()];
        mutableArray = mutableFields.toArray(mutableArray);
        return mutableArray;
    }

    private static boolean isFieldMutable(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }

    private static MethodHandle unreflectGetter(Field field) {
        try {
            return MethodHandles.lookup().unreflectGetter(field);
        }
        catch (IllegalAccessException e) {
            DivineFavor.logger.error("Unable to unreflect getter for field {} in class {}", field.getName(), field.getDeclaringClass());
            e.printStackTrace();
            return null;
        }
    }

    private static MethodHandle unreflectSetter(Field field) {
        try {
            return MethodHandles.lookup().unreflectSetter(field);
        }
        catch (IllegalAccessException e) {
            DivineFavor.logger.error("Unable to unreflect setter for field {} in class {}", field.getName(), field.getDeclaringClass());
            e.printStackTrace();
            return null;
        }
    }
}
