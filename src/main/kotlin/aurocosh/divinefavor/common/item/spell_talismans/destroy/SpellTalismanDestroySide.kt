package aurocosh.divinefavor.common.item.spell_talismans.destroy

import aurocosh.divinefavor.common.coordinate_generators.FloodFillSideCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanDestroySide(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanDestroy(name, spirit, favorCost, options) {
    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val fuzzy = stack.get(isFuzzy)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (!fuzzy && state == world.getBlockState(context.pos))
            return emptyList()

        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)
        return coordinateGenerator.getCoordinates(blockPos, count, world, fuzzy, context.facing)
    }

    companion object {
        private val coordinateGenerator: FloodFillSideCoordinateGenerator = FloodFillSideCoordinateGenerator()
    }
}
