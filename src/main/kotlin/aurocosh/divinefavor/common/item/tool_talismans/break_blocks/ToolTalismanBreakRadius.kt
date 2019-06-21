package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.common.coordinate_generators.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateWallCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanBreakRadius(name: String, spirit: ModSpirit, favorCost: Int) : ToolTalismanBreak(name, spirit, favorCost) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack, world) = context.get(playerField, stackField, worldField)
        val (fuzzy, state) = stack.get(isFuzzy, selectPropertyWrapper.selectedBlock)
        if (!fuzzy && state == world.getBlockState(context.pos))
            return emptyList()

        val radius = stack.get(radius) - 1
        val (facing, blockPos) = context.get(facingField, posField)

        return cachedContainer.getValue(facing, blockPos, radius) {
            val directions = UtilPlayer.getRelativeDirections(player, facing)
            val coordinates = generateWallCoordinates(directions, blockPos, radius, radius, radius, radius)
            if (fuzzy) coordinates else coordinates.filter(world::getBlockState, state::equals)
        }
    }

    override fun getBlockCount(stack: ItemStack): Int {
        val radius = stack.get(radius) - 1
        val width = radius + 1 + radius
        return width * width
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
