package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuildShifted
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class SpellTalismanBuildSphere(name: String, spirit: ModSpirit) : SpellTalismanBuildShifted(name, spirit) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)

    override fun getBlockCount(stack: ItemStack): Int {
        val radius = stack.get(radius)
        val volume = ((4 * Math.PI) / 3) * (radius * radius * radius)
        return volume.toInt()
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val radius = context.stack.get(radius)
        val blockPos = positionPropertyWrapper.getPosition(context)

        return cachedContainer.getValue(blockPos, radius) {
            UtilCoordinates.getBlocksInSphere(blockPos, radius).toList()
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
