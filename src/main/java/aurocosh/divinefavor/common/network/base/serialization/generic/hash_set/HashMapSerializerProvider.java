package aurocosh.divinefavor.common.network.base.serialization.generic.hash_set;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.FieldByteBufSerializers;
import aurocosh.divinefavor.common.network.base.serialization.GenericSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class HashMapSerializerProvider implements GenericSerializerProvider<HashMap> {
    public BufReader<HashMap> getReader(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufReader keyReader = FieldByteBufSerializers.getReader(keyType);
        BufReader valueReader = FieldByteBufSerializers.getReader(valueType);
        //noinspection unchecked
        return new HashMapReader(keyReader, valueReader);
    }

    public BufWriter<HashMap> getWriter(ParameterizedType type) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];

        BufWriter keyWriter = FieldByteBufSerializers.getWriter(keyType);
        BufWriter valueWriter = FieldByteBufSerializers.getWriter(valueType);
        //noinspection unchecked
        return new HashMapWriter(keyWriter, valueWriter);
    }
}
