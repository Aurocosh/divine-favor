package aurocosh.divinefavor.common.item.bathing_blend.base

import aurocosh.divinefavor.common.config.entries.items.BathingBlendPotion
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

class ItemBathingBlendModPotion(name: String, duration: Int, rate: Int, private val potion: ModPotion, private val extraPotionDuration: Int) : ItemBathingBlend(name, duration, rate) {

    constructor(name: String, potion: ModPotion, settings: BathingBlendPotion) : this(name, settings.duration, settings.rate, potion, settings.extraPotionDuration)

    override fun applyEffect(livingBase: EntityLivingBase) {
        var duration = extraPotionDuration
        val potionEffect = livingBase.activePotionMap[potion]
        if (potionEffect != null)
            duration += potionEffect.duration
        livingBase.addPotionEffect(ModEffect(potion, duration))
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}