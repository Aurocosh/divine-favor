package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.SphereCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildSphere(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildShifted(name, spirit, favorCost, options) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)

    override fun getBlockCount(stack: ItemStack): Int {
        val radius = stack.get(radius)
        val volume = ((4 * Math.PI) / 3) * (radius * radius * radius)
        return volume.toInt()
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val radius = context.stack.get(radius)
        val blockPos = positionPropertyWrapper.getPosition(context, context.pos)
        return coordinateGenerator.getCoordinates(blockPos, radius)
    }

    companion object {
        private val coordinateGenerator: SphereCoordinateGenerator = SphereCoordinateGenerator()
    }
}
