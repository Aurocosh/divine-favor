package aurocosh.divinefavor.common.item.spell_talismans.destroy

import aurocosh.divinefavor.common.coordinate_generators.CuboidCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanDestroyCuboid(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanDestroy(name, spirit, favorCost, options) {
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
        val (player, stack, world) = context.getCommon()
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val directions = UtilPlayer.getRelativeDirections(player, context.facing)
        val sequence = coordinateGenerator.getCoordinates(directions, blockPos, up, down, left, right, depth)
        if (stack.get(isFuzzy))
            return sequence.toList()
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        return sequence.filter(world::getBlockState, state::equals).toList()
    }

    companion object {
        private val coordinateGenerator: CuboidCoordinateGenerator = CuboidCoordinateGenerator()
    }
}
