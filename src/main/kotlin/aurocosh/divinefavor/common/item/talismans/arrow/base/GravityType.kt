package aurocosh.divinefavor.common.item.talismans.arrow.base

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class GravityType private constructor() {
    NORMAL,
    NO_GRAVITY,
    ANTIGRAVITY;

    companion object : IIndexedEnum<GravityType> {
        override val indexer: EnumIndexer<GravityType> = EnumIndexer(values())
    }
}