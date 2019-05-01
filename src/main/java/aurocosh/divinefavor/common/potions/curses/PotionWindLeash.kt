package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.config.data.DoubleInterval
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncWindLeash
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.Vec3d

class PotionWindLeash : ModPotion("wind_leash", false, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)

        val x = directionInterval.random()
        val z = directionInterval.random()
        val vec3d = Vec3d(x, 0.0, z)

        var vector = vec3d.normalize().scale(ConfigArrow.windLeash.motionSpeed.toDouble())
        if (livingBase is EntityPlayer)
            vector = vector.scale(ConfigArrow.windLeash.playerMultiplier.toDouble())
        LivingData.get(livingBase).windLeashData.vector = vector

        MessageSyncWindLeash(vector).sendTo(livingBase)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val windLeash = LivingData.get(livingBase).windLeashData
        val vector = windLeash.vector

        livingBase.motionX = vector.x
        livingBase.motionZ = vector.z

        if (livingBase.world.isRemote)
            return
        if (isLookingInDirection(livingBase, windLeash.normalizedVector))
            livingBase.removePotionEffect(ModCurses.wind_leash)
    }

    private fun isLookingInDirection(livingBase: EntityLivingBase, vec3d: Vec3d): Boolean {
        return vec3d.dotProduct(livingBase.lookVec) >= ConfigArrow.windLeash.tolerance
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val directionInterval = DoubleInterval(-1.0, 1.0)
    }
}
