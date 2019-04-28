package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionPetrification : ModPotion("petrification", true, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer) {
            MessagePetrificationReset().sendTo(livingBase)
        } else {
            val petrification = LivingData.get(livingBase).petrificationData
            petrification.resetCureTimer()
        }
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase is EntityPlayer)
            performForPlayer(livingBase)
        else
            performForMob(livingBase)
    }

    private fun performForMob(notPlayer: EntityLivingBase) {
        if (notPlayer.world.isRemote)
            return
        val petrification = LivingData.get(notPlayer).petrificationData
        if (notPlayer.motionX == 0.0 && notPlayer.motionZ == 0.0) {
            if (petrification.cureTick())
                notPlayer.removePotionEffect(ModCurses.petrification)
        } else if (petrification.damageTick())
            notPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, ConfigArrow.petrification.damage)
    }

    private fun performForPlayer(player: EntityPlayer) {
        if (!player.world.isRemote)
            return
        val petrification = LivingData.get(player).petrificationData
        if (player.motionX == 0.0 && player.motionZ == 0.0) {
            if (petrification.cureTick()) {
                player.removePotionEffect(ModCurses.petrification)
                MessagePetrificationCure().send()
            }
        } else if (petrification.damageTick())
            MessagePetrificationDamage().send()
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
