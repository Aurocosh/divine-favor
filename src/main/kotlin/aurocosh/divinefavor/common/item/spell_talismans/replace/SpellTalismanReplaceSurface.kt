package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateSurfaceCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.posField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanReplaceSurface(name: String, spirit: ModSpirit) : SpellTalismanReplace(name, spirit) {
    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, pos) = context.get(stackField, worldField, posField)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (state == world.getBlockState(pos))
            return emptyList()

        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)

        return cachedContainer.getValue(blockPos, count, fuzzy) {
            generateSurfaceCoordinates(blockPos, count, world, fuzzy)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
