package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.ExtrusionCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
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
        val (_, stack, world) = context.getCommon()
        val (length, surface) = stack.get(length, surface)
        val origin = positionPropertyWrapper.getPosition(context, context.pos)
        return coordinateGenerator.getCoordinates(context.pos, origin, world, context.facing, surface, length)
    }

    companion object {
        private val coordinateGenerator: ExtrusionCoordinateGenerator = ExtrusionCoordinateGenerator()
    }
}
