package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilArray;

public class EnumIndexer<T extends Enum<T>> {
    private final T[] values;

    public EnumIndexer(T[] values) {
        this.values = values;
    }

    public T[] getValues() {
        return values;
    }

    public T get(int index) {
        return values[clampIndex(index)];
    }

    public int clampIndex(int index) {
        return UtilArray.clampIndex(values, index);
    }
}
