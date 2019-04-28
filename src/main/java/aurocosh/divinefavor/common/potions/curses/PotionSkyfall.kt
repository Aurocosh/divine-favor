package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.math.Vec3d

class PotionSkyfall : ModPotion("skyfall", false, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        livingBase.motionY = upwardMotion.toDouble()
        livingBase.onGround = false
        livingBase.aiMoveSpeed = 0.1f
        if (isLookingUp(livingBase))
            livingBase.removePotionEffect(ModCurses.skyfall)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    private fun isLookingUp(livingBase: EntityLivingBase): Boolean {
        return UP_VECTOR.dotProduct(livingBase.lookVec) >= TOLERANCE
    }

    companion object {
        private val upwardMotion = 0.3f
        private val TOLERANCE = 0.97f
        private val UP_VECTOR = Vec3d(0.0, 1.0, 0.0)
    }
}
