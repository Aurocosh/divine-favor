package aurocosh.divinefavor.common.potions.base.effect

import aurocosh.divinefavor.common.item.common.ModItems
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import java.util.*

open class ModEffect : PotionEffect {
    protected val modPotion: Potion

    constructor(potion: Potion) : super(potion) {
        modPotion = potion
    }

    constructor(potion: Potion, durationIn: Int) : super(potion, durationIn) {
        modPotion = potion
    }

    constructor(potion: Potion, durationIn: Int, amplifierIn: Int) : super(potion, durationIn, amplifierIn) {
        modPotion = potion
    }

    fun setIsCurse(): ModEffect {
        curativeItems = listOf(ItemStack(ModItems.milky_apple))
        return this
    }

    fun setIsSuperCurse(): ModEffect {
        curativeItems = ArrayList()
        return this
    }


}
