package aurocosh.divinefavor.common.network.base.serialization.generic.array_list;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.FieldByteBufSerializers;
import aurocosh.divinefavor.common.network.base.serialization.GenericSerializerProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayListSerializerProvider implements GenericSerializerProvider<ArrayList> {
    public BufReader<ArrayList> getReader(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufReader reader = FieldByteBufSerializers.getReader(actualType);
        return new ArrayListReader(reader);
    }

    public BufWriter<ArrayList> getWriter(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        BufWriter writer = FieldByteBufSerializers.getWriter(actualType);
        return new ArrayListWriter(writer);
    }
}
