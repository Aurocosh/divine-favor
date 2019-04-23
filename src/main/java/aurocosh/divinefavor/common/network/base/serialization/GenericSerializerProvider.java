package aurocosh.divinefavor.common.network.base.serialization;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;

import java.lang.reflect.ParameterizedType;

public interface GenericSerializerProvider<T> {
    BufReader<T> getReader(ParameterizedType type);
    BufWriter<T> getWriter(ParameterizedType type);
}
