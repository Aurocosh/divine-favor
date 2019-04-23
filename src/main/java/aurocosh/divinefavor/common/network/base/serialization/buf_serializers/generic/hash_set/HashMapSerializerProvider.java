package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider;
import aurocosh.divinefavor.common.network.base.interfaces.GenericSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class HashMapSerializerProvider implements GenericSerializerProvider<HashMap> {
    public BufReader<HashMap> getReader(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufReader keyReader = TypeBufSerializerProvider.getReader(keyType);
        BufReader valueReader = TypeBufSerializerProvider.getReader(valueType);
        //noinspection unchecked
        return new HashMapReader(keyReader, valueReader);
    }

    public BufWriter<HashMap> getWriter(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufWriter keyWriter = TypeBufSerializerProvider.getWriter(keyType);
        BufWriter valueWriter = TypeBufSerializerProvider.getWriter(valueType);
        //noinspection unchecked
        return new HashMapWriter(keyWriter, valueWriter);
    }
}
