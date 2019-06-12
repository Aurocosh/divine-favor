package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanFlakArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        doFlakStuff(spellArrow)
        return true
    }

    override fun onUpdate(spellArrow: EntitySpellArrow) {
        if (!spellArrow.world.isRemote)
            doFlakStuff(spellArrow)
    }

    private fun doFlakStuff(spellArrow: EntitySpellArrow) {
        if (spellArrow.isInGround)
            return

        val livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB(spellArrow.position).grow(ConfigArrow.flakArrow.radius.toDouble()))
        if (livingBases.isEmpty())
            return
        livingBases.firstOrNull { element -> !element.onGround && element.getDistanceSq(spellArrow) <= radiusSq }
                ?: return

        val arrowPosition = spellArrow.position
        spellArrow.world.newExplosion(spellArrow, arrowPosition.x.toDouble(), arrowPosition.y.toDouble(), arrowPosition.z.toDouble(), ConfigArrow.flakArrow.explosionPower, false, false)
        spellArrow.setDead()
    }

    companion object {
        private val radiusSq = ConfigArrow.flakArrow.radius * ConfigArrow.flakArrow.radius
    }
}
