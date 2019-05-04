package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isIce
import aurocosh.divinefavor.common.lib.wrapper.AreaPredicate
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanLakeThawing(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world

        val icePredicate = ConvertingPredicate(world::getBlock, Block::isIce)
        val neighbours = BlockPosConstants.HORIZONTAL_DIRECT.asSequence().plus(BlockPosConstants.DOWN).toList()
        val airPredicate = AreaPredicate(world::getBlock, { block -> block !== Blocks.AIR }, neighbours, neighbours.size)

        val predicate = UtilPredicate.and(icePredicate::invoke, airPredicate::invoke)

        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.lakeThawing.radius)
        val startingPoints = spherePoints.filter(predicate).toList()

        val pointsToUnfreeze = UtilCoordinates.floodFill(startingPoints, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, ConfigSpells.lakeThawing.floodLimit)

        val state = Blocks.WATER.defaultState
        val task = BlockProcessingTask(pointsToUnfreeze, world, 1) { pos: BlockPos ->
            UtilBlock.replaceBlock(context.player, world, pos, state)
        }
        task.start()
    }
}
