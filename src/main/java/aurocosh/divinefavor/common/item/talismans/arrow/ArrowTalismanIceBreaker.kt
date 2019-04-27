package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanIceBreaker(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun performActionServer(target: EntityLivingBase, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos, sideHit: EnumFacing): Boolean {
        val world = spellArrow.world

        val predicate = ConvertingPredicate(world::getBlock) { block: Block -> UtilBlock.isIce(block) }
        val posList = UtilCoordinates.floodFill(listOf(blockPos), BlockPosConstants.DIRECT_AND_DIAGONAL, predicate::invoke, limit)

        for (pos in posList)
            UtilBlock.removeBlock(shooter as EntityPlayer, world, ItemStack.EMPTY, pos, true, false, false)
        return true
    }

    companion object {
        private const val limit = 600
    }
}
