package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.common.coordinate_generators.generateFloodFillCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.posField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanDestroyBlocks(name: String, spirit: ModSpirit) : ToolTalismanDestroy(name, spirit) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)

    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (stack, world, pos) = context.get(stackField, worldField, posField)
        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        return cachedContainer.getValue(pos, count, fuzzy) {
            generateFloodFillCoordinates(pos, count, world, fuzzy)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
