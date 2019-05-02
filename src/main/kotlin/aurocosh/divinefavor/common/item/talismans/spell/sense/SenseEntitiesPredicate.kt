package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class SenseEntitiesPredicate {
    HOSTILE,
    PASSIVE,
    PLAYERS,
    ALL;

    companion object : IIndexedEnum<SenseEntitiesPredicate> {
        override val indexer: EnumIndexer<SenseEntitiesPredicate> = EnumIndexer(values())
    }
}