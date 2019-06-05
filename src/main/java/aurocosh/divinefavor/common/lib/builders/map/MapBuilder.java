package aurocosh.divinefavor.common.lib.builders.map;

import java.util.Collections;
import java.util.Map;

public class MapBuilder<K, V> extends BaseBuilder<Map<K, V>, K, V> {
    private boolean unmodifiable;

    public MapBuilder(Map<K, V> map) {
        super(map);
    }

    @Override
    public MapBuilder<K, V> put(K key, V value) {
        super.put(key, value);
        return this;
    }

    public MapBuilder<K, V> unmodifiable(boolean unmodifiable) {
        this.unmodifiable = unmodifiable;
        return this;
    }

    @Override
    public Map<K, V> build() {
        if (unmodifiable)
            return Collections.unmodifiableMap(super.build());
        else
            return super.build();
    }
}
