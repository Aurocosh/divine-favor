package aurocosh.divinefavor.common.item.wishing_stones

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import java.util.*

object ModWishingStones {
    val wishingStones: MutableList<ItemWishingStone> = ArrayList()

    fun preInit() {
        val spirits = ModMappers.spirits.values
        for (spirit in spirits)
            wishingStones.add(ItemWishingStone(spirit, ConfigItem.minorWishingStoneFavorValue, "minor", ConstGemTabOrder.CALLING_STONE))
    }
}