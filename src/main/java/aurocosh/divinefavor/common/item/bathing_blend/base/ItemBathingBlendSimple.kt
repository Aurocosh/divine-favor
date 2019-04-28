package aurocosh.divinefavor.common.item.bathing_blend.base

import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

abstract class ItemBathingBlendSimple(name: String, private val duration: Int, private val rate: Int) : ItemBathingBlend(name) {

    abstract override fun applyEffect(livingBase: EntityLivingBase)

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun makeStack(count: Int): ItemStack {
        val stack = ItemStack(this, count)
        val nbt = UtilNbt.getNbt(stack)
        nbt.setInteger(TAG_RATE, rate)
        nbt.setInteger(TAG_DURATION, duration)
        return stack
    }
}