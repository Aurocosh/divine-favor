package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.coordinate_generators.FloodFillSideCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanReplaceSide(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanReplace(name, spirit, favorCost, options) {
    override fun getBlockCount(stack: ItemStack): Int = favorCost * blockCount.getValue(stack)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        if (state == world.getBlockState(context.pos))
            return emptyList()

        val fuzzy = stack.get(isFuzzy)
        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)
        return coordinateGenerator.getCoordinates(blockPos, count, world, fuzzy, context.facing)
    }

    companion object {
        private val coordinateGenerator: FloodFillSideCoordinateGenerator = FloodFillSideCoordinateGenerator()
    }
}
