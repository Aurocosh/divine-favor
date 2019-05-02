package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.util.IStringSerializable

enum class SoulboundLecternState private constructor(private val value: String) : IStringSerializable {
    INACTIVE("inactive"),
    ACTIVE("active");

    override fun getName(): String {
        return value
    }

    companion object : IIndexedEnum<SoulboundLecternState> {
        override val indexer: EnumIndexer<SoulboundLecternState> = EnumIndexer(values())
    }
}