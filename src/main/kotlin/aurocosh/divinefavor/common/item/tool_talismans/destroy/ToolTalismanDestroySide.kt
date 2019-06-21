package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.common.coordinate_generators.FloodFillSideCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanDestroySide(name: String, spirit: ModSpirit, favorCost: Int) : ToolTalismanDestroy(name, spirit, favorCost) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val fuzzy = stack.get(isFuzzy)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (!fuzzy && state == world.getBlockState(context.pos))
            return emptyList()

        val count = getBlockCount(stack)
        return coordinateGenerator.getCoordinates(context.pos, count, world, fuzzy, context.facing)
    }

    companion object {
        private val coordinateGenerator: FloodFillSideCoordinateGenerator = FloodFillSideCoordinateGenerator()
    }
}
