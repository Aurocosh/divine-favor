package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.EntityLivingBase

class PotionExtremeBuoyancy : ModPotionToggle("extreme_buoyancy", true, 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val pos = livingBase.position

        if (!UtilBlock.isLiquid(livingBase.world.getBlockState(pos).block))
            return
        if (livingBase.isSneaking)
            return

        livingBase.motionY = ConfigSpells.extremeBuoyancy.buoyancyForce.toDouble()
        livingBase.aiMoveSpeed = 0.1f
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
