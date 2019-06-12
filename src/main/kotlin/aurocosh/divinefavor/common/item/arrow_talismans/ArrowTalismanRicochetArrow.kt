package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.add
import aurocosh.divinefavor.common.lib.extensions.getMotionVector
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanRicochetArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun preInit(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        val compound = spellArrow.talismanDataServer
        compound.setInteger(TAG_BOUNCES_LEFT, ConfigArrow.ricochetArrow.maxBounces)
    }

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target != null)
            return true
        if(sideHit == null)
            return true

        val compound = spellArrow.talismanDataServer
        val bouncesLeft = compound.getInteger(TAG_BOUNCES_LEFT)
        if (bouncesLeft == 0)
            return true
        compound.setInteger(TAG_BOUNCES_LEFT, bouncesLeft - 1)

        val motionVector = spellArrow.getMotionVector()
        val speed = motionVector.length().toFloat()
        if (speed < ConfigArrow.ricochetArrow.minBounceSpeed)
            return true

        val sideVectorI = sideHit.directionVec
        val randomChange = UtilRandom.nextDirection().scale(ConfigArrow.ricochetArrow.bounceRandomness)
        val direction = motionVector.normalize().add(sideVectorI).add(randomChange)
        UtilEntity.setVelocity(spellArrow, direction, speed * ConfigArrow.ricochetArrow.bounceSpeedDecrease)
        return false
    }

    companion object {
        private val TAG_BOUNCES_LEFT = "BouncesLeft"
    }
}
