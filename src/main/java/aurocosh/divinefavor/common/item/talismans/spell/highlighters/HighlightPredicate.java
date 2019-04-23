package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.lib.EnumIndexer;
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum;

public enum HighlightPredicate implements IndexedEnum<HighlightPredicate> {
    BLOCK(0),
    WATER(1),
    LAVA(2),
    LIQUID(3),
    ORE(4);

    public static final EnumIndexer<HighlightPredicate> INDEXER = new EnumIndexer<>(HighlightPredicate.values());

    private final int index;

    HighlightPredicate(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}