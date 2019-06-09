package aurocosh.divinefavor.common.item.bathing_blend.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

abstract class ItemBathingBlend(name: String, val duration: Int, val rate: Int) : ModItem("blend_$name", "blends/$name", ConstMainTabOrder.BLENDS) {

    init {
        creativeTab = DivineFavor.TAB_MAIN
    }

    abstract fun applyEffect(livingBase: EntityLivingBase)

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}