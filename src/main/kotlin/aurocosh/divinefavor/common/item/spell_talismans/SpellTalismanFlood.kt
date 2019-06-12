package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.getMaterial
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.BlockLiquid
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanFlood(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val posList = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpell.flood.radius)
        val flowingWaterBlocks = posList
                .filter(world::getMaterial, Material.WATER::equals)
                .filter(world::getBlockState) { state -> state.getValue(BlockLiquid.LEVEL) != 0 }

        val state = Blocks.WATER.defaultState
        for (pos in flowingWaterBlocks)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
