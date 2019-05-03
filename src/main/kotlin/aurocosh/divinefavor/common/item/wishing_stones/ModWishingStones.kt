package aurocosh.divinefavor.common.item.wishing_stones

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import java.util.*

object ModWishingStones {
    val wishingStones: MutableList<ItemWishingStone> = ArrayList()

    fun preInit() {
        for (spirit in ModMappers.spirits.values)
            wishingStones.add(ItemWishingStone(spirit, ConfigItem.minorWishingStoneFavorValue, "minor", ConstGemTabOrder.WISHING_STONE))
    }
}