package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.getMotionVector
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import java.awt.Color
import java.util.*

class ArrowTalismanPiercingArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun preInit(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        val compound = spellArrow.talismanDataCommon
        compound.setInteger(TAG_HITS_LEFT, ConfigArrow.piercingArrow.maxHits)
        compound.setInteger(TAG_LAST_HIT_ENTITY, 0)
        spellArrow.talismanDataCommon = compound
    }

    override fun performActionClient(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target == null)
            return true
        val compound = spellArrow.talismanDataCommon
        if (compound.getInteger(TAG_LAST_HIT_ENTITY) == target.entityId)
            return false
        return compound.getInteger(TAG_HITS_LEFT) == 0
    }

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target == null)
            return true
        val compound = spellArrow.talismanDataCommon
        if (compound.getInteger(TAG_LAST_HIT_ENTITY) == target.entityId)
            return false
        val hitsLeft = compound.getInteger(TAG_HITS_LEFT)
        if (hitsLeft == 0)
            return true

        val motionSpeed = spellArrow.getMotionVector().length()
        var damage = MathHelper.ceil(motionSpeed * spellArrow.damage)

        if (spellArrow.isCritical)
            damage += UtilRandom.nextInt(0, damage / 2 + 2)

        val damageSource: DamageSource = if (spellArrow.shootingEntity == null)
            DamageSource.causeArrowDamage(spellArrow, spellArrow)
        else
            DamageSource.causeArrowDamage(spellArrow, spellArrow.shootingEntity)

        target.attackEntityFrom(damageSource, damage.toFloat())
        compound.setInteger(TAG_HITS_LEFT, hitsLeft - 1)
        compound.setInteger(TAG_LAST_HIT_ENTITY, target.entityId)
        spellArrow.talismanDataCommon = compound
        spellArrow.damage = spellArrow.damage + ConfigArrow.piercingArrow.damageIncreasePerHit
        return false
    }

    companion object {
        private val TAG_HITS_LEFT = "HitsLeft"
        private val TAG_LAST_HIT_ENTITY = "LastHitEntity"
    }
}
