package aurocosh.divinefavor.common.lib.enums

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class InventoryIndexes(val value: Int) {
    HotBarStart(0),
    HotBarEnd(8),
    MainStart(9),
    MainEnd(35),
    CraftSlotsStart(36),
    CraftSlotsEnd(39),
    Offhand(40),

    MinSlotIndex(0),
    MaxSlotIndex(40);

    companion object : IIndexedEnum<InventoryIndexes> {
        override val indexer: EnumIndexer<InventoryIndexes> = EnumIndexer(values())
    }
}