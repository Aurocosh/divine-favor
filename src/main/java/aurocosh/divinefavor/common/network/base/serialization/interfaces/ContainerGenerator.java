package aurocosh.divinefavor.common.network.base.serialization.interfaces;

@FunctionalInterface
public interface ContainerGenerator<T> {
    T get(int capacity);
}
