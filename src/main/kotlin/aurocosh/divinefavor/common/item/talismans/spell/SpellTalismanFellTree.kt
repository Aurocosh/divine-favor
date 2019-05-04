package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.isLeaves
import aurocosh.divinefavor.common.lib.extensions.isWood
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockBreakingTask
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.util.math.BlockPos
import java.util.EnumSet
import kotlin.collections.ArrayList

class SpellTalismanFellTree(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val logs = detectTree(context)
        if (logs.isNotEmpty()) {
            val talisman = context.player.getHeldItem(context.hand)
            BlockBreakingTask(logs, context.player, talisman, ConfigSpells.fellTree.breakingSpeed).start()
        }
    }

    private fun detectTree(context: TalismanContext): List<BlockPos> {
        val world = context.world
        val start = listOf(context.pos)
        val logs = UtilCoordinates.floodFill(start, BlockPosConstants.DIRECT_NEIGHBOURS, world::isWood, ConfigSpells.fellTree.maxLogsBroken)
        if (logs.isEmpty())
            return logs

        val possibleLeaves = logs.map { logPos -> BlockPosConstants.DIRECT_NEIGHBOURS.map(logPos::add) }.flatten().toSet()
        val leaves = UtilCoordinates.floodFill(possibleLeaves, BlockPosConstants.DIRECT_NEIGHBOURS, world::isLeaves, ConfigSpells.fellTree.minLeafCount)
        if (leaves.size < ConfigSpells.fellTree.minLeafCount)
            return ArrayList()
        return logs
    }
}
