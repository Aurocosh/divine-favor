package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class SenseBlockPredicate {
    BLOCK,
    WATER,
    LAVA,
    LIQUID,
    ORE,
    AIR;

    companion object : IIndexedEnum<SenseBlockPredicate> {
        override val indexer: EnumIndexer<SenseBlockPredicate> = EnumIndexer(values())
    }
}