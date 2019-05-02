package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.config.data.DoubleInterval
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncRedFury
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.Vec3d

class PotionRedFury : ModPotion("red_fury", false, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)

        if (livingBase !is EntityPlayer) {
            livingBase.removePotionEffect(ModCurses.red_fury)
            return
        }

        val x = directionInterval.random()
        val y = directionInterval.random()
        val z = directionInterval.random()
        val vector = Vec3d(x, y, z).scale(ConfigPunishments.redwind.dragSpeed.toDouble())

        livingBase.divineCustomData.redFuryData.vector = vector
        MessageSyncRedFury(vector).sendTo(livingBase)
        return
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val player = livingBase as EntityPlayer
        val furyData = player.divineCustomData.redFuryData
        val vector = furyData.vector
        player.motionX = vector.x
        player.motionY = vector.y
        player.motionZ = vector.z
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val directionInterval = DoubleInterval(-1.0, 1.0)
    }
}
