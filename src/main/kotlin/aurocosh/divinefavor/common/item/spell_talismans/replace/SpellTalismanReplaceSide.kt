package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateSideCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanReplaceSide(name: String, spirit: ModSpirit) : SpellTalismanReplace(name, spirit) {
    override fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, facing) = context.get(stackField, worldField, facingField)
        val fuzzy = stack.get(isFuzzy)

        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)
        val state = world.getBlockState(blockPos)

        return cachedContainer.getValue(facing, blockPos, count, fuzzy) {
            val predicate: (IBlockState) -> Boolean = { fuzzy || it == state }
            generateSideCoordinates(blockPos, count, world, facing, predicate)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
