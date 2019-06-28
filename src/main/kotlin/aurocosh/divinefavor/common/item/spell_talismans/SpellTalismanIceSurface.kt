package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.lib.wrapper.AreaPredicate
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockProcessingTask
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import java.util.*

class SpellTalismanIceSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val floodLimit: StackPropertyInt;
    private val test: StackPropertyInt;
    private val testB: StackPropertyBool;

    init {
        val limit = ConfigSpell.iceSurface.floodLimit
        floodLimit = propertyHandler.registerIntProperty("freeze_limit", limit, 1, limit)
        test = propertyHandler.registerIntProperty("test", 10, 5, 20)
        testB = propertyHandler.registerBoolProperty("safety_lock", false)
    }

    override fun performActionServer(context: TalismanContext) {
        val value = testB.getValue(context.stack)
        if(value)
        {
            context.player.sendStatusMessage(TextComponentTranslation("locked"), true)
            return
        }

        val world = context.world
        val limit = floodLimit.getValue(context.stack)

        val waterPredicate = ConvertingPredicate(world::getBlock, Block::isWater)
        val airPredicate = AreaPredicate(world::getBlock, Blocks.AIR::equals, BlockPosConstants.DIRECT_NEIGHBOURS, 1)
        val predicate = UtilPredicate.and(waterPredicate::invoke, airPredicate::invoke)

        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpell.iceSurface.radius)
        val startingPoints = spherePoints.filter(predicate).toList()
        val pointsToFreeze = UtilCoordinates.floodFill(startingPoints, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, limit)

        val state = Blocks.ICE.defaultState
        val task = BlockProcessingTask(pointsToFreeze, world, 1) { pos: BlockPos ->
            UtilBlock.replaceBlock(context.player, world, pos, state)
        }
        task.start()
    }
}
