package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.coordinate_generators.generateFloodFillCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanBreakBlocks(name: String, spirit: ModSpirit) : ToolTalismanBreak(name, spirit) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (stack, world, pos, facing) = context.get(stackField, worldField, posField, facingField)
        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        val state = world.getBlockState(pos)

        return cachedContainer.getValue(facing, pos, count, fuzzy) {
            val predicate: (BlockPos, IBlockState) -> Boolean = { _, blockState -> blockState == state }
            generateFloodFillCoordinates(pos, count, world, fuzzy, predicate)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
