package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.generateWallCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PlayerRotationPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanBuildSquareWall(name: String, spirit: ModSpirit) : SpellTalismanBuildShifted(name, spirit) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)
    private val rotationPropertyWrapper = PlayerRotationPropertyWrapper(propertyHandler)

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (player, stack) = context.get(playerField, stackField)
        val radius = stack.get(radius) - 1

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)

        return cachedContainer.getValue(facing, blockPos, radius) {
            val directions = UtilPlayer.getRelativeDirections(player, facing)
            generateWallCoordinates(directions, blockPos, 2 * radius, 0, radius, radius)
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
