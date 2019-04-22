package aurocosh.divinefavor.common.network.base.serialization;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassByteBufSerializer {
    private final Field[] fields;
    private final BufWriter[] writers;
    private final BufReader[] readers;

    public ClassByteBufSerializer(Class clazz) {
        fields = getClassFields(clazz);
        writers = FieldByteBufSerializers.getFieldWriters(fields);
        readers = FieldByteBufSerializers.getFieldReaders(fields);
    }

    public final void fromBytes(Object object, ByteBuf buf) {
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                BufReader reader = readers[i];
                field.set(object, reader.read(buf));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error at reading packet " + object, e);
        }
    }

    public final void toBytes(Object object, ByteBuf buf) {
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                BufWriter writer = writers[i];
                writer.write(buf, field.get(object));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error at writing packet " + object, e);
        }
    }

    private static Field[] getClassFields(Class<?> clazz) {
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
}
