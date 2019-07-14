package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class AreaCorner {
    First,
    Second;

    companion object : IIndexedEnum<AreaCorner> {
        override val indexer: EnumIndexer<AreaCorner> = EnumIndexer(values())
    }
}