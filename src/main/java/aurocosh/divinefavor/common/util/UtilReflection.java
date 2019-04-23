package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.DivineFavor;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UtilReflection {
    public static Field getPrivateField(Class classToAccess, int fieldIndex) {
        try {
            Field f = classToAccess.getDeclaredFields()[fieldIndex];
            f.setAccessible(true);
            return f;
        }
        catch (ReflectionHelper.UnableToAccessFieldException e) {
            FMLLog.log.error("There was a problem getting field index {} from {}", fieldIndex, classToAccess.getName(), e);
            throw e;
        }
    }

    public static Field[] getMutableFields(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> mutableFields = new ArrayList<>();
        for (Field field : fields)
            if (isFieldMutable(field))
                mutableFields.add(field);
        mutableFields.sort(Comparator.comparing(Field::getName));
        return mutableFields.toArray(new Field[0]);
    }

    private static boolean isFieldMutable(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }

    public static MethodHandle unreflectGetter(Field field) {
        try {
            return MethodHandles.lookup().unreflectGetter(field);
        }
        catch (IllegalAccessException e) {
            DivineFavor.logger.error("Unable to unreflect getter for field {} in class {}", field.getName(), field.getDeclaringClass());
            e.printStackTrace();
            return null;
        }
    }

    public static MethodHandle unreflectSetter(Field field) {
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
