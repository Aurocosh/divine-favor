package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import java.util.*

class SpellTalismanSearingPulse(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockLimit: StackPropertyInt = propertyHandler.registerIntProperty("block_limit", ConfigSpell.searingPulse.maxBlocksSmelted)

    override fun getApproximateFavorCost(itemStack: ItemStack): Int {
        return favorCost * blockLimit.getValue(itemStack)
    }

    override fun performActionServer(context: CastContext) {
        val world = context.world
        val block = world.getBlock(context.pos)
        if(!UtilBlock.smeltBlock(context.player, world, context.pos))
            return

        val blocksToSmelt = blockLimit.getValue(context.stack) - 1
        val predicate = ConvertingPredicate(world::getBlock, block::equals)
        val start = BlockPosConstants.DIRECT_NEIGHBOURS.map(context.pos::add)
        val toSmelt = UtilCoordinates.floodFill(start, BlockPosConstants.DIRECT_NEIGHBOURS, predicate::invoke, blocksToSmelt)

        val task = BlockProcessingTask(toSmelt, world, 5) { pos: BlockPos ->
            UtilBlock.smeltBlock(context.player, world, pos)
        }
        task.start()
    }
}
