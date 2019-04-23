package aurocosh.divinefavor.common.lib.functional_interfaces;

@FunctionalInterface
public interface ComparePredicate<T> {
    boolean isFirstBetter(T first, T second);
}
