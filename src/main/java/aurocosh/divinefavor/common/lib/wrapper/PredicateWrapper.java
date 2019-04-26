package aurocosh.divinefavor.common.lib.wrapper;

import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateWrapper<T, K> implements Predicate<T> {
    private final Function<T, K> converter;
    private final Predicate<K> predicate;

    public PredicateWrapper(Function<T, K> converter, Predicate<K> predicate) {
        this.converter = converter;
        this.predicate = predicate;
    }

    public boolean test(T value) {
        K converted = converter.apply(value);
        return predicate.test(converted);
    }
}
