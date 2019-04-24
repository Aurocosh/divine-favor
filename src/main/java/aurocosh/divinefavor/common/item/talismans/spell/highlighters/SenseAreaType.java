package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.lib.EnumIndexer;
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum;

public enum SenseAreaType implements IndexedEnum<SenseAreaType> {
    SPHERE(0),
    DELAYED_FLOOD_FILL(1);

    public static final EnumIndexer<SenseAreaType> INDEXER = new EnumIndexer<>(SenseAreaType.values());

    private final int index;

    SenseAreaType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}