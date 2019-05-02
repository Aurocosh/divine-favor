package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.selectRandom
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanSearingPulse(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val nodesToVisit = ArrayDeque<BlockPos>()
        val visitedNodes = HashSet<BlockPos>()
        nodesToVisit.add(context.pos)

        var cycleLimit = CYCLE_LIMIT
        var blocksToSmelt = UtilRandom.nextInt(ConfigSpells.searingPulse.minBlocksToSmelt, ConfigSpells.searingPulse.maxBlocksToSmelt)
        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && blocksToSmelt-- > 0) {
            val nextNode = nodesToVisit.remove()
            visitedNodes.add(nextNode)

            if (UtilRandom.rollDiceFloat(ConfigSpells.searingPulse.chanceToCreateFire)) {
                UtilBlock.ignite(context.player, context.world, nextNode)
                continue
            }
            if (!UtilBlock.smeltBlock(context.player, context.world, nextNode))
                continue

            val neighboursToAdd = UtilRandom.nextInt(ConfigSpells.searingPulse.minNeighboursToAdd, ConfigSpells.searingPulse.maxNeighboursToAdd)
            val dirs = BlockPosConstants.DIRECT_NEIGHBOURS.selectRandom(neighboursToAdd)
            for (dir in dirs) {
                val node = nextNode.add(dir)
                if (!visitedNodes.contains(node))
                    nodesToVisit.add(node)
            }
        }
    }

    companion object {
        private val CYCLE_LIMIT = 300
    }
}
