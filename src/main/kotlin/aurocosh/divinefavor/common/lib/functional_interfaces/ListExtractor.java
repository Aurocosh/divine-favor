package aurocosh.divinefavor.common.lib.functional_interfaces;

import java.util.List;

@FunctionalInterface
public interface ListExtractor<T, K> {
    List<T> extract(K value);
}
