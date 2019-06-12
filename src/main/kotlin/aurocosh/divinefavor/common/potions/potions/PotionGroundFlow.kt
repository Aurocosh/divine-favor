package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

class PotionGroundFlow : ModPotionToggle("ground_flow", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer)
            livingBase.removePotionEffect(ModPotions.ground_flow)
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase is EntityPlayer)
            UtilPlayer.setAllowFlying(livingBase, false)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase !is EntityPlayer)
            return
        val allowFlying = livingBase.position.y <= ConfigSpell.groundFlow.yLimit
        UtilPlayer.setAllowFlying(livingBase, allowFlying)
        if (!allowFlying)
            livingBase.removePotionEffect(ModPotions.ground_flow)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
