package aurocosh.divinefavor.common.block.bath_heater

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.util.IStringSerializable

enum class BathHeaterState(private val value: String) : IStringSerializable {
    INACTIVE("inactive"),
    ACTIVE("active");

    override fun getName(): String {
        return value
    }

    companion object : IIndexedEnum<BathHeaterState> {
        override val indexer: EnumIndexer<BathHeaterState> = EnumIndexer(values())
    }
}