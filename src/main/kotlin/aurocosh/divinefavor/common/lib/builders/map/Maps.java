package aurocosh.divinefavor.common.lib.builders.map;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Maps {
    private Maps() {
    }

    public static <K, V> MapBuilder<K, V> builder() {
        return builder(HashMap::new);
    }

    public static <K, V> MapBuilder<K, V> builder(Supplier<Map<K, V>> mapSupplier) {
        return new MapBuilder<>(mapSupplier.get());
    }
}
