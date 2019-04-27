package aurocosh.divinefavor.common.item.talismans.spell.sense;

import aurocosh.divinefavor.common.lib.EnumIndexer;
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum;

public enum SenseEntitiesPredicate implements IndexedEnum<SenseEntitiesPredicate> {
    HOSTILE(0),
    PASSIVE(1),
    PLAYERS(2),
    ALL(3);

    public static final EnumIndexer<SenseEntitiesPredicate> INDEXER = new EnumIndexer<>(SenseEntitiesPredicate.values());

    private final int index;

    SenseEntitiesPredicate(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}