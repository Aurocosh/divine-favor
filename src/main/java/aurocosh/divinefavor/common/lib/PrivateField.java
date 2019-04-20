package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.util.ReflectionUtils;

import java.lang.reflect.Field;

public class PrivateField<T, K> {
    private final Field field;
    private Class<T> classToAccess;
    private final K fallbackValue;

    public PrivateField(Class<T> classToAccess, int fieldIndex, K fallbackValue) {
        this.classToAccess = classToAccess;
        this.fallbackValue = fallbackValue;
        this.field = ReflectionUtils.getPrivateField(classToAccess, fieldIndex);
        if (!field.getType().equals(fallbackValue.getClass()))
            DivineFavor.logger.error("Reflected private field {} on class {} value type {} is not equal to expected value type {}", field.getName(), classToAccess.getName(), field.getType().getName(), fallbackValue.getClass().getName());
    }

    public K get(T instance) {
        try {
            return (K) field.get(instance);
        }
        catch (IllegalAccessException e) {
            DivineFavor.logger.error("Unable to access any field {} on type {} with type {}", field.getName(), classToAccess.getName(), e);
            e.printStackTrace();
        }
        return fallbackValue;
    }
}
