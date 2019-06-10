package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.gems.ItemFavorMark
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import java.util.*

object ModFavorMarks {
    val wishingStones: MutableList<ItemFavorMark> = ArrayList()

    fun preInit() {
        for (spirit in ModMappers.spirits.values)
            wishingStones.add(ItemFavorMark(spirit, ConstGemTabOrder.FAVOR_MARK))
    }
}