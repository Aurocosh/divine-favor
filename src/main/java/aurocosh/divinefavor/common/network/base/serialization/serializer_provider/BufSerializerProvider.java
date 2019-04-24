package aurocosh.divinefavor.common.network.base.serialization.serializer_provider;

import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.EnumSerializer;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.GenericSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.serializer_provider.registration.BufSerializerRegistryEvent;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BufSerializerProvider {
    private static final Map<Class, BufWriter> WRITERS = new HashMap<>();
    private static final Map<Class, BufReader> READERS = new HashMap<>();

    private static final Map<Class, EnumSerializer> ENUM_SERIALIZERS = new HashMap<>();
    private static final Map<Class, GenericSerializerProvider> GENERIC_PROVIDERS = new HashMap<>();

    public static void preInit() {
        if (WRITERS.isEmpty() && READERS.isEmpty())
            MinecraftForge.EVENT_BUS.post(new BufSerializerRegistryEvent());
    }

    public static <T> void registerWriter(Class<T> clazz, BufWriter<T> writer) {
        WRITERS.putIfAbsent(clazz, writer);
    }

    public static <T> void registerReader(Class<T> clazz, BufReader<T> reader) {
        READERS.putIfAbsent(clazz, reader);
    }

    public static <T> void registerSerializerProvider(Class<T> clazz, GenericSerializerProvider<T> provider) {
        GENERIC_PROVIDERS.putIfAbsent(clazz, provider);
    }

    public static BufReader getReader(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return GENERIC_PROVIDERS.get(rawType).getReader(parameterizedType);
        }
        if (type instanceof Class) {
            Class clazz = (Class) type;
            if (clazz.isEnum())
                return ENUM_SERIALIZERS.computeIfAbsent(clazz, EnumSerializer::new);
            return READERS.get(clazz);
        }
        return null;
    }

    public static BufWriter getWriter(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return GENERIC_PROVIDERS.get(rawType).getWriter(parameterizedType);
        }
        if (type instanceof Class) {
            Class clazz = (Class) type;
            if (clazz.isEnum())
                return ENUM_SERIALIZERS.computeIfAbsent(clazz, EnumSerializer::new);
            return WRITERS.get(clazz);
        }
        return null;
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
