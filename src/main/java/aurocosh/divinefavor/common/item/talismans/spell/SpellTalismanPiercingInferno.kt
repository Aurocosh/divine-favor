package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanPiercingInferno(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val facing = context.facing

        var blocksToBreak = if (context.player.isSneaking) ConfigSpells.piercingInferno.blocksToBreakWeak else ConfigSpells.piercingInferno.blocksToBreakNormal

        val expansionDirs = ArrayList(BlockPosConstants.DIRECT_NEIGHBOURS)
        val shift = BlockPos(facing.opposite.directionVec)
        expansionDirs.remove(shift)
        expansionDirs.remove(UtilBlockPos.inverse(shift))

        val predicate = { pos: BlockPos ->
            val state = world.getBlockState(pos)
            state.isSideSolid(world, pos, facing) && world.isAirBlock(pos.offset(facing))
        }

        var piercingShape = UtilCoordinates.floodFill(listOf(context.pos), expansionDirs, predicate, blocksToBreak)
        val shapeSize = piercingShape.size
        blocksToBreak -= shapeSize

        val blocksToRemove = ArrayList<BlockPos>()
        while (blocksToBreak > 0) {
            var i = 0
            while (blocksToBreak-- > 0 && i < shapeSize) {
                blocksToRemove.add(piercingShape[i])
                i++
            }
            piercingShape = piercingShape.map { pos -> pos.add(shift) }
        }

        val processor: (BlockPos) -> Unit = { pos: BlockPos ->
            val ignite = UtilRandom.rollDice(ConfigSpells.piercingInferno.chanceToIgnite.toFloat())
            val block = if (ignite) Blocks.FIRE else Blocks.AIR
            UtilBlock.replaceBlock(context.player, context.world, pos, block.defaultState)
        }
        BlockProcessingTask(blocksToRemove, world, 1, processor).start()
    }
}
