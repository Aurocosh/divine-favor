package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateWallCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanBuildWall(name: String, spirit: ModSpirit) : SpellTalismanBuildShifted(name, spirit) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val height: StackPropertyInt = propertyHandler.registerIntProperty("height", 3, 1, 20)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

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

    override fun getBlockCount(stack: ItemStack): Int {
        val (left, right, height) = stack.get(left, right, height)
        val width = left + 1 + right
        return width * height
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
