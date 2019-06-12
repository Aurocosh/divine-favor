package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanForceArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, arrowType: ArrowType, private val velocity: Float) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), arrowType) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target == null)
            return false
        if (target !is EntityPlayer)
            UtilEntity.addVelocity(target, shooter.lookVec, velocity)
        return true
    }
}
