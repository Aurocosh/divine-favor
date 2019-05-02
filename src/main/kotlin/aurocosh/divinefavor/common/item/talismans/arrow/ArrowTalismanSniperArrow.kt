package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.getVec3d
import aurocosh.divinefavor.common.lib.extensions.setVec3d
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanSniperArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, 0.0, options, arrowType) {

    override fun init(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        spellArrow.talismanDataServer.setVec3d(TAG_STARTING_POS, shooter.positionVector)
    }

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        val startingPosition = spellArrow.talismanDataServer.getVec3d(TAG_STARTING_POS)

        val distance = startingPosition.distanceTo(spellArrow.positionVector)
        spellArrow.damage = if (distance < ConfigArrow.sniperArrow.minDistance) 0.0 else ConfigArrow.sniperArrow.damagePerMeter * distance
        return true
    }

    companion object {
        private val TAG_STARTING_POS = "StartingPos"
    }
}
