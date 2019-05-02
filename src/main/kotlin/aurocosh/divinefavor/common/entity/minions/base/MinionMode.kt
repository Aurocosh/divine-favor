package aurocosh.divinefavor.common.entity.minions.base

import aurocosh.divinefavor.common.lib.EnumIndexer

enum class MinionMode private constructor(val value: Int) {
    Normal(0),
    Follow(1),
    Wait(2);

    companion object {
        // Optimization
        public val INDEXER = EnumIndexer(values())
    }
}