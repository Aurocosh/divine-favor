package aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.array_list;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider;
import aurocosh.divinefavor.common.network.base.interfaces.GenericSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayListSerializerProvider implements GenericSerializerProvider<ArrayList> {
    public BufReader<ArrayList> getReader(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufReader reader = TypeBufSerializerProvider.getReader(actualType);
        return new ArrayListReader(reader);
    }

    public BufWriter<ArrayList> getWriter(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufWriter writer = TypeBufSerializerProvider.getWriter(actualType);
        return new ArrayListWriter(writer);
    }
}
