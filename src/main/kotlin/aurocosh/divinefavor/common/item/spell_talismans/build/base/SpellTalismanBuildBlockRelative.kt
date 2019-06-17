package aurocosh.divinefavor.common.item.spell_talismans.build.base

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.SpellTalismanBuildRelative
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildBlockRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuildRelative(name, spirit, favorCost, options) {
    override fun getBlockCount(stack: ItemStack) = 1

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val (player, stack) = context.getCommon()
        val coordinates = getCoordinates(context)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        return listOf(positionPropertyWrapper.getPosition(context))
    }
}
