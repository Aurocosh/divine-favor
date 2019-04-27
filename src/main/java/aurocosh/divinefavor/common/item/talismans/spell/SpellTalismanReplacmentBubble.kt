package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.ReplacmentBubble
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilList
import net.minecraft.block.Block

class SpellTalismanReplacmentBubble(name: String, spirit: ModSpirit, config: ReplacmentBubble, private val blockInternal: Block, private val blockExternal: Block, private val predicate: (Block) -> Boolean) : ItemSpellTalisman(name, spirit, config.favorCost, SpellOptions.ALL_CAST) {
    private val radius: Int

    init {
        radius = config.radius
    }

    override fun performActionServer(context: TalismanContext) {
        val radiusInternal = radius - 1
        val radiusInternalSq = radiusInternal * radiusInternal

        val world = context.world
        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, radius)
        var internalPoints = UtilList.split(spherePoints) { pos -> pos.distanceSq(context.pos) < radiusInternalSq }

        val blockPredicate = BlockPredicate(world, predicate)
        posList = UtilList.select(spherePoints) { pos ->  predicate.invoke(world.getBlock(pos))}
        internalPoints = UtilList.select(internalPoints, blockPredicate)

        var state = blockExternal.defaultState
        for (pos in posList)
            UtilBlock.replaceBlock(context.player, world, pos, state)

        state = blockInternal.defaultState
        for (pos in internalPoints)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
