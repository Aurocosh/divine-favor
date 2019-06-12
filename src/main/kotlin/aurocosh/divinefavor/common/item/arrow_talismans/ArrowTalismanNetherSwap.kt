package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanNetherSwap(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), arrowType) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        val livingBase = target ?: return false
        val targetPos = livingBase.position
        val shooterPos = shooter.position
        UtilEntity.teleport(shooter, targetPos)
        UtilEntity.teleport(livingBase, shooterPos)
        return true
    }
}
