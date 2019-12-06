package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.generateCylinderCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.facing
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanBuildCylinder(name: String, spirit: ModSpirit) : SpellTalismanBuildShifted(name, spirit) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 0, 15)
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 1, 1, 50)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    override fun getCoordinates(context: CastContext): List<BlockPos> {
        val (player, stack) = context.get(playerField, stackField)
        val (radius, length) = context.stack.get(radius, length)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val facing = rotationPropertyWrapper.getRotation(stack, player.facing)

        return cachedContainer.getValue(facing, blockPos, radius, length) {
            val directions = UtilPlayer.getRelativeDirections(player, facing)
            generateCylinderCoordinates(directions, blockPos, radius, length)
        }
    }

    override fun getBlockCount(stack: ItemStack): Int {
        val (radius, length) = stack.get(radius, length)
        val surface = Math.PI * radius * radius
        return (surface * length).toInt()
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
