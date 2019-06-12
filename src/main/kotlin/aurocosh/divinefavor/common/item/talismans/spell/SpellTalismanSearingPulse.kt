package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanSearingPulse(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world

        val blocksToSmelt = UtilRandom.nextInt(ConfigSpell.searingPulse.minBlocksToSmelt, ConfigSpell.searingPulse.maxBlocksToSmelt)

        val block = world.getBlock(context.pos)
        if(!UtilBlock.smeltBlock(context.player, world, context.pos))
            return

        val predicate = ConvertingPredicate(world::getBlock, block::equals)
        val start = BlockPosConstants.DIRECT_NEIGHBOURS.map(context.pos::add).toList()
        val toSmelt = UtilCoordinates.floodFill(start, BlockPosConstants.DIRECT_NEIGHBOURS, predicate::invoke, blocksToSmelt - 1)

        val task = BlockProcessingTask(toSmelt, world, 5) { pos: BlockPos ->
            UtilBlock.smeltBlock(context.player, world, pos)
        }
        task.start()
    }
}
