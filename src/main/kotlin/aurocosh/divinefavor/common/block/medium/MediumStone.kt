package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.util.IStringSerializable

enum class MediumStone(private val value: String) : IStringSerializable {
    NONE("none"),
    ARBOW("arbow"),
    BLIZRABI("blizrabi"),
    ENDERERER("endererer"),
    LOON("loon"),
    NEBLAZE("neblaze"),
    REDWIND("redwind"),
    ROMOL("romol"),
    SQUAREFURY("squarefury"),
    TIMBER("timber");

    override fun getName(): String {
        return value
    }

    companion object : IIndexedEnum<MediumStone> {
        override val indexer: EnumIndexer<MediumStone> = EnumIndexer(values())
    }
}