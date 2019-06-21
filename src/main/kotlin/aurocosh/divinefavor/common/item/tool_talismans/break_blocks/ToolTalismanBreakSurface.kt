package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.coordinate_generators.SurfaceCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanBreakSurface(name: String, spirit: ModSpirit, favorCost: Int) : ToolTalismanBreak(name, spirit, favorCost) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val stack = context.stack
        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        return coordinateGenerator.getCoordinates(context.pos, count, context.world, fuzzy)
    }

    companion object {
        private val coordinateGenerator: SurfaceCoordinateGenerator = SurfaceCoordinateGenerator()
    }
}
