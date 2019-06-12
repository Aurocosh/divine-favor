package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionWildCharge : ModPotion("wild_charge", 0x7FB8A4) {
    init {
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "817e7bc4-8ad5-4323-9131-aa71236a1b83", (-ConfigSpell.wildSprint.slownessForce).toDouble(), 2)
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        livingBase.addPotionEffect(ModEffect(ModPotions.wild_sprint, ConfigSpell.wildSprint.speedDuration, ConfigSpell.wildSprint.speedLevel).setIsCurse())
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
