package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.common.lib.EnumIndexer;
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum;

public enum GravityType implements IndexedEnum<GravityType> {
    NORMAL(0),
    NO_GRAVITY(1),
    ANTIGRAVITY(2);

    public static final EnumIndexer<GravityType> INDEXER = new EnumIndexer<>(GravityType.values());

    private final int index;

    GravityType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}