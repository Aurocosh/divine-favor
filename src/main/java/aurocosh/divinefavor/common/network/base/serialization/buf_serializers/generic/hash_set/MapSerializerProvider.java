package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set;

import aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.ContainerGenerator;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.GenericSerializerProvider;

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

        BufReader keyReader = TypeBufSerializerProvider.getReader(keyType);
        BufReader valueReader = TypeBufSerializerProvider.getReader(valueType);
        return new MapReader<>(containerGenerator, keyReader, valueReader);
    }

    public BufWriter<T> getWriter(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufWriter keyWriter = TypeBufSerializerProvider.getWriter(keyType);
        BufWriter valueWriter = TypeBufSerializerProvider.getWriter(valueType);
        return new MapWriter<>(keyWriter, valueWriter);
    }
}
