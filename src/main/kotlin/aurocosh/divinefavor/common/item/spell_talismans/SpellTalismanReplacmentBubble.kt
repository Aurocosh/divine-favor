package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.entries.spell_talismans.generic.ReplacmentBubble
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.split
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState

class SpellTalismanReplacmentBubble(name: String, spirit: ModSpirit, config: ReplacmentBubble, private val blockInternal: Block, private val blockExternal: Block, private val blockPredicate: (IBlockState) -> Boolean) : ItemSpellTalisman(name, spirit, config.favorCost, SpellOptions.ALL_CAST) {
    private val radius: Int = config.radius

    override fun performActionServer(context: TalismanContext) {
        val radiusInternal = radius - 1
        val radiusInternalSq = radiusInternal * radiusInternal

        val world = context.world
        val validPoints = UtilCoordinates.getBlocksInSphere(context.pos, radius)
                .filter(world::getBlockState, blockPredicate)
        val split = validPoints.split { pos -> pos.distanceSq(context.pos) < radiusInternalSq }

        val stateInternal = blockInternal.defaultState
        for (pos in split.first)
            UtilBlock.replaceBlock(context.player, world, pos, stateInternal)

        val stateExternal = blockExternal.defaultState
        for (pos in split.second)
            UtilBlock.replaceBlock(context.player, world, pos, stateExternal)
    }
}
