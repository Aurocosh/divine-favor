package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.wrapper.BlockAreaPredicate
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanIceSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world

        val waterPredicate = BlockPredicate(world) { UtilBlock.isWater(it) }::test
        val airPredicate = BlockAreaPredicate(world, Blocks.AIR, BlockPosConstants.DIRECT_NEIGHBOURS, 1)::test
        val predicate = {pos : BlockPos -> waterPredicate.invoke(pos) && airPredicate.invoke(pos)};

        val spherePosList = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.iceSurface.radius)

        var posToFrees = spherePosList.filter(predicate)
        posToFrees = UtilCoordinates.floodFill(posToFrees, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, ConfigSpells.iceSurface.floodLimit)

        val state = Blocks.ICE.defaultState
        for (pos in posToFrees)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
