package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.lib.EnumIndexer
import net.minecraft.util.IStringSerializable

enum class SoulboundLecternGem private constructor(private val value: String) : IStringSerializable {
    NONE("none"),
    END("end"),
    MIND("mind"),
    NETHER("nether"),
    PEACE("peace"),
    WILL("will"),
    UNDEATH("undeath"),
    WATER("water"),
    WILD("wild"),
    WITHER("wither");

    override fun getName(): String {
        return value
    }

    companion object {
        val INDEXER = EnumIndexer(values())
    }
}