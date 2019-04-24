package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.lib.EnumIndexer;
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum;

public enum SenseBlockPredicate implements IndexedEnum<SenseBlockPredicate> {
    BLOCK(0),
    WATER(1),
    LAVA(2),
    LIQUID(3),
    ORE(4);

    public static final EnumIndexer<SenseBlockPredicate> INDEXER = new EnumIndexer<>(SenseBlockPredicate.values());

    private final int index;

    SenseBlockPredicate(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}