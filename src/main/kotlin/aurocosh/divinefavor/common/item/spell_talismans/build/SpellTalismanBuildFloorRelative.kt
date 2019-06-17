package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.FloorCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildFloorRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildRelative(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val front: StackPropertyInt = propertyHandler.registerIntProperty("front", 3, 0, 10)
    private val back: StackPropertyInt = propertyHandler.registerIntProperty("back", 0, 0, 10)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right, front, back) = stack.get(left, right, front, back)
        val width = left + 1 + right
        val thickness = front + 1 + back
        return width * thickness
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack) = context.getCommon()
        val (left, right, up, down) = context.stack.get(left, right, front, back)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)
        return coordinateGenerator.getCoordinates(facing, blockPos, up, down, left, right)
    }

    companion object {
        private val coordinateGenerator: FloorCoordinateGenerator = FloorCoordinateGenerator()
    }
}
