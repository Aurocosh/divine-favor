package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanDestructiveArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType, private val maxHardness: Float) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun performActionServer(target: EntityLivingBase, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos, sideHit: EnumFacing): Boolean {
        val world = spellArrow.world
        if (world.isAirBlock(blockPos))
            return true

        val blockState = world.getBlockState(blockPos)
        val hardness = blockState.getBlockHardness(world, blockPos)
        if (hardness <= maxHardness)
            UtilBlock.removeBlock(shooter as EntityPlayer, world, ItemStack.EMPTY, blockPos, true, false, true)
        return true
    }
}
