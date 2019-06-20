package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.WallCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildHorizontalLine(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildShifted(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack) = context.getCommon()
        val (left, right) = context.stack.get(left, right)

        val blockPos = positionPropertyWrapper.getPosition(context, context.pos)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)
        val directions = UtilPlayer.getRelativeDirections(player, facing)
        return coordinateGenerator.getCoordinates(directions, blockPos, 0, 0, left, right)
    }

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right) = stack.get(left, right)
        return left + 1 + right
    }

    companion object {
        private val coordinateGenerator: WallCoordinateGenerator = WallCoordinateGenerator()
    }
}
