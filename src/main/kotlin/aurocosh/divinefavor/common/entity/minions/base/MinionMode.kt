package aurocosh.divinefavor.common.entity.minions.base

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class MinionMode private constructor(val value: Int) {
    Normal(0),
    Follow(1),
    Wait(2);

    companion object : IIndexedEnum<MinionMode> {
        override val indexer: EnumIndexer<MinionMode> = EnumIndexer(values())
    }
}