package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isLiquid
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import net.minecraft.entity.EntityLivingBase

class PotionInstantDive : ModPotionToggle("instant_dive", 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val pos = livingBase.position

        if(!livingBase.world.getBlock(pos).isLiquid())
            return
        if (livingBase.isSneaking)
            return

        livingBase.motionY = (-ConfigSpell.instantDive.force).toDouble()
        livingBase.aiMoveSpeed = 0.1f
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
