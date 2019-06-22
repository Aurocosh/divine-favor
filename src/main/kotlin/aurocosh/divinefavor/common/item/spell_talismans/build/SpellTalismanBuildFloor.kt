package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateFloorCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanBuildFloor(name: String, spirit: ModSpirit) : SpellTalismanBuildShifted(name, spirit) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val front: StackPropertyInt = propertyHandler.registerIntProperty("front", 3, 0, 10)
    private val back: StackPropertyInt = propertyHandler.registerIntProperty("back", 0, 0, 10)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack) = context.get(playerField, stackField)
        val (left, right, front, back) = stack.get(left, right, front, back)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)

        return cachedContainer.getValue(facing, blockPos, front, back, left, right) {
            generateFloorCoordinates(facing, blockPos, front, back, left, right)
        }
    }

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right, front, back) = stack.get(left, right, front, back)
        val width = left + 1 + right
        val thickness = front + 1 + back
        return width * thickness
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
