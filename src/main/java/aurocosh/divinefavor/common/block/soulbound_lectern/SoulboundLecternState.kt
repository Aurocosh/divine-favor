package aurocosh.divinefavor.common.block.soulbound_lectern

import net.minecraft.util.IStringSerializable

enum class SoulboundLecternState private constructor(private val value: String) : IStringSerializable {
    INACTIVE("inactive"),
    ACTIVE("active");

    override fun getName(): String {
        return value
    }

    companion object {

        // Optimization
        val VALUES = SoulboundLecternState.values()
    }
}