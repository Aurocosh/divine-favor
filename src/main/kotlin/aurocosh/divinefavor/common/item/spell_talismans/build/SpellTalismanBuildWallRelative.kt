package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateWallCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildWallRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildRelative(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val height: StackPropertyInt = propertyHandler.registerIntProperty("height", 3, 1, 20)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right, height) = stack.get(left, right, height)
        val width = left + 1 + right
        return favorCost * (width * height)
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack) = context.get(playerField, stackField)
        val (left, right, height) = context.stack.get(left, right, height)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)

        return cachedContainer.getValue(facing, blockPos, height, left, right) {
            val directions = UtilPlayer.getRelativeDirections(player, facing)
            generateWallCoordinates(directions, blockPos, height - 1, 0, left, right)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
