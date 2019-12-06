package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateCuboid
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanReplaceCuboid(name: String, spirit: ModSpirit) : SpellTalismanReplace(name, spirit) {
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

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (player, stack, world, facing) = context.get(playerField, stackField, worldField, facingField)
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)

        val state = stack.get(selectPropertyWrapper.selectedBlock)
        val blockPos = positionPropertyWrapper.getPosition(context)

        return cachedContainer.getValue(facing, blockPos, up, down, left, right, depth) {
            val directions = UtilPlayer.getRelativeDirections(player, context.facing)
            val sequence = generateCuboid(directions, blockPos, up, down, left, right, depth)
            if (stack.get(isFuzzy))
                sequence.toList()
            else
                sequence.filter(world::getBlockState) { state != it }.toList()
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
