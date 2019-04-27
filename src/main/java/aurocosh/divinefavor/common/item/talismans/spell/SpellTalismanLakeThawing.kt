package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.wrapper.AreaPredicate
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilList
import aurocosh.divinefavor.common.util.UtilPredicateKot
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanLakeThawing(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world

        val icePredicate = ConvertingPredicate(world::getBlock, UtilBlock::isIce)
        val neighbours = UtilList.unite(BlockPosConstants.HORIZONTAL_DIRECT, BlockPosConstants.DOWN)
        val airPredicate = AreaPredicate(world::getBlock, { block -> block !== Blocks.AIR }, neighbours, neighbours.size)

        val predicate = UtilPredicateKot.and(icePredicate::invoke, airPredicate::invoke)

        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.lakeThawing.radius)
        val startingPoints = spherePoints.filter(predicate)

        val pointsToFreeze = UtilCoordinates.floodFill(startingPoints, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, ConfigSpells.lakeThawing.floodLimit)

        val state = Blocks.WATER.defaultState
        for (pos in pointsToFreeze)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
