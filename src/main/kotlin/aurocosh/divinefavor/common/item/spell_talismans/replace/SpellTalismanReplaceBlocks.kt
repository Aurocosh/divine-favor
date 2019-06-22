package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateFloodFillCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanReplaceBlocks(name: String, spirit: ModSpirit) : SpellTalismanReplace(name, spirit) {
    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world) = context.get(stackField, worldField)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (state == world.getBlockState(context.pos))
            return emptyList()

        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)

        return cachedContainer.getValue(blockPos, count, fuzzy) {
            generateFloodFillCoordinates(blockPos, count, world, fuzzy)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
