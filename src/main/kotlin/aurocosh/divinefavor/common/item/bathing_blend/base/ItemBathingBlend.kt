package aurocosh.divinefavor.common.item.bathing_blend.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList

abstract class ItemBathingBlend(name: String) : ModItem("blend_$name", "blends/$name", ConstMainTabOrder.BLENDS) {

    init {
        creativeTab = DivineFavor.TAB_MAIN
    }

    abstract fun applyEffect(livingBase: EntityLivingBase)

    abstract fun makeStack(count: Int): ItemStack

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (!isInCreativeTab(tab))
            return
        items.add(makeStack(1))
    }

    companion object {
        public val TAG_RATE = "rate"
        public val TAG_DURATION = "duration"

        fun getDuration(stack: ItemStack): Int {
            return UtilNbt.getNbt(stack).getInteger(TAG_DURATION)
        }

        fun getRate(stack: ItemStack): Int {
            return UtilNbt.getNbt(stack).getInteger(TAG_RATE)
        }
    }
}