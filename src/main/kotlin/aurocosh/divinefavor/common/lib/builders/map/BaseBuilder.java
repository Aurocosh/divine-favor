package aurocosh.divinefavor.common.lib.builders.map;

import java.util.Map;

public class BaseBuilder<M extends Map<K, V>, K, V> {
    protected final M map;

    public BaseBuilder(M map) {
        this.map = map;
    }

    public BaseBuilder<M, K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public M build() {
        return map;
    }
}