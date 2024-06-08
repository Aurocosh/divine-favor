package aurocosh.autonetworklib.network.serialization.interfaces;

import java.lang.reflect.ParameterizedType;

public interface GenericSerializerProvider<T> {
    BufReader<T> getReader(ParameterizedType type);
    BufWriter<T> getWriter(ParameterizedType type);
}
