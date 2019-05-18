package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
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
        target.attackEntityFrom(DamageSource.causePlayerDamage(shooter as EntityPlayer), ConfigArrow.lifeStealArrow.damage)
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
