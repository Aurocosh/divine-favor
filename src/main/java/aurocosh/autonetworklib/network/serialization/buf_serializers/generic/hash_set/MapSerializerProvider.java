package aurocosh.autonetworklib.network.serialization.buf_serializers.generic.hash_set;

import aurocosh.autonetworklib.network.serialization.interfaces.BufReader;
import aurocosh.autonetworklib.network.serialization.interfaces.BufWriter;
import aurocosh.autonetworklib.network.serialization.interfaces.ContainerGenerator;
import aurocosh.autonetworklib.network.serialization.interfaces.GenericSerializerProvider;
import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class MapSerializerProvider<T extends Map<Object, Object>> implements GenericSerializerProvider<T> {
    private final ContainerGenerator<T> containerGenerator;

    public MapSerializerProvider(ContainerGenerator<T> containerGenerator) {
        this.containerGenerator = containerGenerator;
    }

    public BufReader<T> getReader(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufReader keyReader = BufSerializerProvider.getReader(keyType);
        BufReader valueReader = BufSerializerProvider.getReader(valueType);
        return new MapReader<>(containerGenerator, keyReader, valueReader);
    }

    public BufWriter<T> getWriter(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufWriter keyWriter = BufSerializerProvider.getWriter(keyType);
        BufWriter valueWriter = BufSerializerProvider.getWriter(valueType);
        return new MapWriter<>(keyWriter, valueWriter);
    }
}
