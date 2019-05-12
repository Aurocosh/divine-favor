package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.lib.extensions.getPreviousPosition
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing

class PotionHovering : ModPotionToggle("hovering", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer)
            livingBase.removePotionEffect(ModPotions.hovering)
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase is EntityPlayer)
            UtilPlayer.setAllowFlying(livingBase, false)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val world = livingBase.world
        if (world.isRemote)
            return

        var allowFlying = livingBase.position == livingBase.getPreviousPosition()
        if (!allowFlying) {
            val pos = UtilCoordinates.findPosition(livingBase.position, 10,
                    { blockPos -> world.getBlockState(blockPos).isSideSolid(world, blockPos, EnumFacing.UP) },
                    { it.down() })
            allowFlying = pos != null
        }

        UtilPlayer.setAllowFlying(livingBase as EntityPlayer, allowFlying)
        if (!allowFlying)
            livingBase.removePotionEffect(ModPotions.hovering)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
