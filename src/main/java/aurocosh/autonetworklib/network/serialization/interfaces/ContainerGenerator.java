package aurocosh.autonetworklib.network.serialization.interfaces;

@FunctionalInterface
public interface ContainerGenerator<T> {
    T get(int capacity);
}
