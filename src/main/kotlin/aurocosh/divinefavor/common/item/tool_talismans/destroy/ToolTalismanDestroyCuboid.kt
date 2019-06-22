package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateCuboid
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ToolTalismanDestroyCuboid(name: String, spirit: ModSpirit) : ToolTalismanDestroy(name, spirit) {
    private val up: StackPropertyInt = propertyHandler.registerIntProperty("up", 1, 0, 10)
    private val down: StackPropertyInt = propertyHandler.registerIntProperty("down", 1, 0, 10)
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 1, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 1, 0, 10)
    private val depth: StackPropertyInt = propertyHandler.registerIntProperty("depth", 3, 1, 20)

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right, up, down, depth) = stack.get(left, right, up, down, depth)
        val width = left + 1 + right
        val height = up + 1 + down
        return favorCost * (width * height * depth)
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (facing, pos) = context.get(facingField, posField)
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)

        return cachedContainer.getValue(facing, pos, up, down, left, right, depth) {
            val (player, stack, world) = context.get(playerField, stackField, worldField)
            val directions = UtilPlayer.getRelativeDirections(player, context.facing)
            val sequence = generateCuboid(directions, pos, up, down, left, right, depth)
            val state = stack.get(selectPropertyWrapper.selectedBlock)
            if (stack.get(isFuzzy))
                sequence.toList()
            else
                sequence.filter(world::getBlockState, state::equals).toList()
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
