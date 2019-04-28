package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum

enum class SenseBlockPredicate(private val index: Int) : IndexedEnum<SenseBlockPredicate> {
    BLOCK(0),
    WATER(1),
    LAVA(2),
    LIQUID(3),
    ORE(4),
    AIR(5);

    override fun getIndex(): Int {
        return index
    }

    companion object {
        val INDEXER = EnumIndexer(values())
    }
}