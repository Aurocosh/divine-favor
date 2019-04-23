package aurocosh.divinefavor.common.util;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

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
}
