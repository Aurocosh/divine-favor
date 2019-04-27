package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getMaterial
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.lib.extensions.filter
import net.minecraft.block.BlockLiquid
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanFlood(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val posList = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.flood.radius)
        val flowingWaterBlocks = posList
                .filter(world::getMaterial, Material.WATER::equals)
                .filter(world::getBlockState) { state -> state.getValue(BlockLiquid.LEVEL) !== 0 }

        val state = Blocks.WATER.defaultState
        for (pos in flowingWaterBlocks)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
