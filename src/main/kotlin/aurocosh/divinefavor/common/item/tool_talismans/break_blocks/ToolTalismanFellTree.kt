package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.coordinate_generators.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateTreeCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import aurocosh.divinefavor.common.item.spell_talismans.context.*

class ToolTalismanFellTree(name: String, spirit: ModSpirit, favorCost: Int) : ToolTalismanBreak(name, spirit, favorCost) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 500)

    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = toolClasses
    override fun isHarvestCustom(stack: ItemStack, state: IBlockState) = true
    override fun getCustomHarvest(stack: ItemStack, state: IBlockState) = state.material == Material.WOOD

    override fun getBlockCount(stack: ItemStack): Int = favorCost * stack.get(blockCount)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (pos, world, stack) = context.get(posField, worldField, stackField)
        val count = stack.get(blockCount)

        return cache.getValue(pos, world, count) {
            generateTreeCoordinates(pos, world, count)
        }
    }

    companion object {
        val toolClasses = setOf("axe")
        private val cache = CachedContainer<List<BlockPos>> { emptyList() }
    }
}
