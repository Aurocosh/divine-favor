package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.EnumIndexer

enum class InventoryIndexes(val value: Int) {
    HotBarStart(0),
    HotBarEnd(8),
    MainStart(9),
    MainEnd(35),
    CraftSlotsStart(36),
    CraftSlotsEnd(39),
    Offhand(40);

    companion object {
        val indexer = EnumIndexer<InventoryIndexes>(values())
    }
}