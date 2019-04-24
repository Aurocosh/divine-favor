package aurocosh.divinefavor.common.network.base.serialization.class_serializers;

import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.EnumSerializer;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.GenericSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TypeBufSerializerProvider {
    private static final Map<Class, BufWriter> WRITERS = new HashMap<>();
    private static final Map<Class, BufReader> READERS = new HashMap<>();

    private static final Map<Class, EnumSerializer> ENUM_SERIALIZERS = new HashMap<>();
    private static final Map<Class, GenericSerializerProvider> GENERIC_PROVIDERS = new HashMap<>();

    public static <T> void registerWriter(Class<T> clazz, BufWriter<T> writer) {
        WRITERS.put(clazz, writer);
    }

    public static <T> void registerReader(Class<T> clazz, BufReader<T> reader) {
        READERS.put(clazz, reader);
    }

    public static <T> void registerSerializerProvider(Class<T> clazz, GenericSerializerProvider<T> provider) {
        GENERIC_PROVIDERS.put(clazz, provider);
    }

    public static BufReader getReader(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return GENERIC_PROVIDERS.get(rawType).getReader(parameterizedType);
        }
        else if(type instanceof Class && ((Class<?>)type).isEnum()){
            Class enumClazz = (Class) type;
            EnumSerializer serializer = ENUM_SERIALIZERS.computeIfAbsent(enumClazz, EnumSerializer::new);
            return serializer;
        }
        //noinspection SuspiciousMethodCalls
        return READERS.get(type);
    }

    public static BufWriter getWriter(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return GENERIC_PROVIDERS.get(rawType).getWriter(parameterizedType);
        }
        else if(type instanceof Class && ((Class<?>)type).isEnum()){
            Class enumClazz = (Class) type;
            EnumSerializer serializer = ENUM_SERIALIZERS.computeIfAbsent(enumClazz, EnumSerializer::new);
            return serializer;
        }
        //noinspection SuspiciousMethodCalls
        return WRITERS.get(type);
    }

    public static BufReader[] getReaders(Type[] fields) {
        BufReader[] readers = new BufReader[fields.length];
        for (int i = 0; i < fields.length; i++)
            readers[i] = getReader(fields[i]);
        return readers;
    }

    public static BufWriter[] getWriters(Type[] fields) {
        BufWriter[] writers = new BufWriter[fields.length];
        for (int i = 0; i < fields.length; i++)
            writers[i] = getWriter(fields[i]);
        return writers;
    }
}
