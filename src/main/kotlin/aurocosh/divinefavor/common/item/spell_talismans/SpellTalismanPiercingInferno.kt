package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.lib.extensions.inverse
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanPiercingInferno(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val pierceDepth: StackPropertyInt = propertyHandler.registerIntProperty("piercing_inferno_depth", ConfigSpell.piercingInferno.maxPierceDepth)
    private val pierceSurface: StackPropertyInt = propertyHandler.registerIntProperty("piercing_inferno_surface", ConfigSpell.piercingInferno.maxTunnelSurface)

    override fun getFavorCost(itemStack: ItemStack): Int {
        val depth = pierceDepth.getValue(itemStack)
        val surface = pierceSurface.getValue(itemStack)
        return favorCost * depth * surface;
    }

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val facing = context.facing
        val itemStack = context.stack
        val depth = pierceDepth.getValue(itemStack)
        val surface = pierceSurface.getValue(itemStack)


        val expansionDirs = ArrayList(BlockPosConstants.DIRECT_NEIGHBOURS)
        val shift = BlockPos(facing.opposite.directionVec)
        expansionDirs.remove(shift)
        expansionDirs.remove(shift.inverse())

        val predicate = { pos: BlockPos ->
            val state = world.getBlockState(pos)
            state.isSideSolid(world, pos, facing) && world.isAirBlock(pos.offset(facing))
        }

        var piercingShape = UtilCoordinates.floodFill(listOf(context.pos), expansionDirs, predicate, surface)
        val shapeSize = piercingShape.size

        var blocksToBreak = depth * surface
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
            val ignite = UtilRandom.rollDice(ConfigSpell.piercingInferno.chanceToIgnite.toFloat())
            val block = if (ignite) Blocks.FIRE else Blocks.AIR
            UtilBlock.replaceBlock(context.player, context.world, pos, block.defaultState)
        }
        BlockProcessingTask(blocksToRemove, world, 1, processor).start()
    }
}
