package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.util.IStringSerializable

enum class MediumState(private val value: String) : IStringSerializable {
    INVALID("invalid"),
    VALID("valid"),
    ACTIVE("active");

    override fun getName(): String {
        return value
    }

    companion object : IIndexedEnum<MediumState> {
        override val indexer: EnumIndexer<MediumState> = EnumIndexer(values())
    }
}