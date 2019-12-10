package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.common.coordinate_generators.generateSideCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.cached_container.CountingCachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanDestroySide(name: String, spirit: ModSpirit) : ToolTalismanDestroy(name, spirit) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (stack, world, pos, facing) = context.get(stackField, worldField, posField, facingField)
        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        val state = world.getBlockState(pos)

        return cachedContainer.getValue(facing, pos, count, fuzzy) {
            val predicate: (IBlockState) -> Boolean = { fuzzy || it == state }
            generateSideCoordinates(pos, count, world, facing, predicate)
        }
    }

    companion object {
        private val cachedContainer = CountingCachedContainer(UtilTick.secondsToTicks(0.2f)){ emptyList<BlockPos>() }
    }
}
