package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateSurfaceCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.posField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanBreakSurface(name: String, spirit: ModSpirit) : ToolTalismanBreak(name, spirit) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, pos) = context.get(stackField, worldField, posField)
        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)

        return cachedContainer.getValue(pos, count, fuzzy) {
            generateSurfaceCoordinates(pos, count, world, fuzzy)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
