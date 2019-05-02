package aurocosh.divinefavor.common.lib.functional_interfaces;

@FunctionalInterface
public interface DoublePredicate<T, K> {
    boolean test(T first, K second);
}
