package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildShifted(name, spirit, favorCost, options) {
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 24)

    override fun getBlockCount(stack: ItemStack) = stack.get(length)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val count = getBlockCount(context.stack)
        val blockPos = positionPropertyWrapper.getPosition(context, context.pos)
        return coordinateGenerator.getCoordinates(blockPos, EnumFacing.UP, count)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
