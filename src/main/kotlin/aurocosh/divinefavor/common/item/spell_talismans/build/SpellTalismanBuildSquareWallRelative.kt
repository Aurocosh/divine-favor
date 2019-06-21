package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateWallCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.*
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildSquareWallRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildRelative(name, spirit, favorCost, options) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getBlockCount(stack: ItemStack): Int {
        val radius = stack.get(radius) - 1
        val width = radius + 1 + radius
        return favorCost * (width * width)
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (player, stack) = context.get(playerField, stackField)
        val radius = stack.get(radius) - 1

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)

        return cachedContainer.getValue(facing, blockPos, radius) {
            val directions = UtilPlayer.getRelativeDirections(player, facing)
            generateWallCoordinates(directions, blockPos, 2 * radius, 0, radius, radius)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
