package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.coordinate_generators.generateLineCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.FacingPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanBuildFromSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildShifted(name, spirit, favorCost, options) {
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 24)
    private val facingPropertyWrapper = FacingPropertyWrapper(propertyHandler)

    override fun getBlockCount(stack: ItemStack) = stack.get(length)

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val stack = context.stack
        val blockCount = getBlockCount(stack)
        val facing = facingPropertyWrapper.getFacing(stack, context.facing)
        val blockPos = positionPropertyWrapper.getPosition(context, context.pos)

        return cachedContainer.getValue(blockPos, blockCount) {
            generateLineCoordinates(blockPos, facing, blockCount)
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
