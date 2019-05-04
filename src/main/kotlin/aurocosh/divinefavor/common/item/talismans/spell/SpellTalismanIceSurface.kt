package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isWater
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

class SpellTalismanIceSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world

        val waterPredicate = ConvertingPredicate(world::getBlock, Block::isWater)
        val airPredicate = AreaPredicate(world::getBlock, Blocks.AIR::equals, BlockPosConstants.DIRECT_NEIGHBOURS, 1)
        val predicate = UtilPredicate.and(waterPredicate::invoke, airPredicate::invoke)

        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.iceSurface.radius)
        val startingPoints = spherePoints.filter(predicate).toList()
        val pointsToFreeze = UtilCoordinates.floodFill(startingPoints, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, ConfigSpells.iceSurface.floodLimit)

        val state = Blocks.ICE.defaultState
        val task = BlockProcessingTask(pointsToFreeze, world, 1) { pos: BlockPos ->
            UtilBlock.replaceBlock(context.player, world, pos, state)
        }
        task.start()
    }
}
