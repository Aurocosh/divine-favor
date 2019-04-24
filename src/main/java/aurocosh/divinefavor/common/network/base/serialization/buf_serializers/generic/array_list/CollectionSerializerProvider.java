package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.array_list;

import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.ContainerGenerator;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.GenericSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.serializer_provider.BufSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class CollectionSerializerProvider<T extends Collection<Object>> implements GenericSerializerProvider<T> {
    private final ContainerGenerator<T> containerGenerator;

    public CollectionSerializerProvider(ContainerGenerator<T> containerGenerator) {
        this.containerGenerator = containerGenerator;
    }

    public BufReader<T> getReader(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufReader reader = BufSerializerProvider.getReader(actualType);
        return new CollectionListReader<>(containerGenerator, reader);
    }

    public BufWriter<T> getWriter(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufWriter writer = BufSerializerProvider.getWriter(actualType);
        return new CollectionListWriter<>(writer);
    }
}
