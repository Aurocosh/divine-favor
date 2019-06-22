package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateExtrusionCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildExtrusion(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildShifted(name, spirit, favorCost, options) {
    private val surface: StackPropertyInt = propertyHandler.registerIntProperty("surface", 10, 1, 64)
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 32)

    override fun getBlockCount(stack: ItemStack): Int = stack.get(surface) * stack.get(length)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (pos, facing, stack, world) = context.get(posField, facingField, stackField, worldField)
        val (length, surface) = stack.get(length, surface)
        val origin = positionPropertyWrapper.getPosition(context, pos)

        return cachedContainer.getValue(pos, origin, facing, surface, length) {
            generateExtrusionCoordinates(pos, origin, world, facing, surface, length)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
