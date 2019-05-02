package aurocosh.divinefavor.common.item.talismans.arrow.base

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum

enum class GravityType private constructor(private val index: Int) : IndexedEnum<GravityType> {
    NORMAL(0),
    NO_GRAVITY(1),
    ANTIGRAVITY(2);

    override fun getIndex(): Int {
        return index
    }

    companion object : IIndexedEnum<GravityType> {
        override val indexer: EnumIndexer<GravityType> = EnumIndexer(values())
    }
}