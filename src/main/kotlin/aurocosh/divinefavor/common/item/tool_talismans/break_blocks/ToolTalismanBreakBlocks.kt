package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.coordinate_generators.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateSideCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanBreakBlocks(name: String, spirit: ModSpirit, favorCost: Int) : ToolTalismanBreak(name, spirit, favorCost) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, pos, facing) = context.get(stackField, worldField, posField, facingField)
        val (fuzzy, state) = stack.get(isFuzzy, selectPropertyWrapper.selectedBlock)
        if (!fuzzy && state != world.getBlockState(pos))
            return emptyList()
        val count = getBlockCount(stack)
        return cachedContainer.getValue(facing, pos, count) {
            val fuzzyPredicate: (IBlockState) -> Boolean = { true }
            val notFuzzyPredicate: (IBlockState) -> Boolean = { it == state }
            val predicate: (IBlockState) -> Boolean = if (fuzzy) fuzzyPredicate else notFuzzyPredicate
            generateSideCoordinates(pos, count, world, facing, predicate)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
