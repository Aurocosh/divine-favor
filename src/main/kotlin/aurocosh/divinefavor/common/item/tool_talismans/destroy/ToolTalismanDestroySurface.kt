package aurocosh.divinefavor.common.item.tool_talismans.destroy

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

class ToolTalismanDestroySurface(name: String, spirit: ModSpirit) : ToolTalismanDestroy(name, spirit) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, pos) = context.get(stackField, worldField, posField)
        val fuzzy = stack.get(isFuzzy)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (!fuzzy && state == world.getBlockState(pos))
            return emptyList()

        val count = getBlockCount(stack)
        return cachedContainer.getValue(pos, count, fuzzy) {
            generateSurfaceCoordinates(pos, count, world, fuzzy)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
