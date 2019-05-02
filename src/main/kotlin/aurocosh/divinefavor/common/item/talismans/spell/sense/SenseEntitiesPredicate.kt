package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.interfaces.IndexedEnum

enum class SenseEntitiesPredicate(private val index: Int) : IndexedEnum<SenseEntitiesPredicate> {
    HOSTILE(0),
    PASSIVE(1),
    PLAYERS(2),
    ALL(3);

    override fun getIndex(): Int {
        return index
    }

    companion object {
        val INDEXER = EnumIndexer(values())
    }
}