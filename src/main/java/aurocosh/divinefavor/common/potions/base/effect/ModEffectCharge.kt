package aurocosh.divinefavor.common.potions.base.effect

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.PotionEffect
import net.minecraftforge.fml.relauncher.ReflectionHelper

class ModEffectCharge(potion: ModPotionCharge, charges: Int) : ModEffect(potion, 0, charges) {

    fun consumeCharge(): Int {
        val charges = amplifier - 1
        ReflectionHelper.setPrivateValue(PotionEffect::class.java, this, charges, 3)
        return charges
    }

    override fun combine(other: PotionEffect) {
        if (this.modPotion !== other.potion)
            DivineFavor.logger.info("This method should only be called for matching effects!")
        //        int amplifier = other.getAmplifier();
        //        if (amplifier > this.getAmplifier())
        //            ReflectionHelper.setPrivateValue(PotionEffect.class, this, amplifier, 3);
        val charges = Math.max(other.amplifier, this.amplifier)
        ReflectionHelper.setPrivateValue(PotionEffect::class.java, this, charges, 3)
    }

    override fun onUpdate(entityIn: EntityLivingBase): Boolean {
        return amplifier > 0
    }

}