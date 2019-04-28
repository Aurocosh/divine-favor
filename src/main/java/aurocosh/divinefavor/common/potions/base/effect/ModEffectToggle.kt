package aurocosh.divinefavor.common.potions.base.effect

import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.PotionEffect

class ModEffectToggle(potion: ModPotionToggle) : ModEffect(potion, Integer.MAX_VALUE) {
    private var active: Boolean = false

    init {
        active = true
    }

    override fun onUpdate(entityIn: EntityLivingBase): Boolean {
        performEffect(entityIn)
        return active
    }

    override fun combine(other: PotionEffect) {
        active = false
    }
}