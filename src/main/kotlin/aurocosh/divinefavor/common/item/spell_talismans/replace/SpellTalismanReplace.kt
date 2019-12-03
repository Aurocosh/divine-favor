package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuild
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockBuildData
import aurocosh.divinefavor.common.tasks.BlockBuildingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class SpellTalismanReplace(name: String, spirit: ModSpirit) : SpellTalismanBuild(name, spirit, ConfigGeneral.blockReplacingCost), IBlockCatcher {
    protected val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    protected val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    override val positionPropertyWrapper: PositionPropertyWrapper = PositionPropertyWrapper(propertyHandler)

    override fun getCommonCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirBlock(it) }

    override fun performActionServer(context: TalismanContext) {
        val (player, stack) = context.getCommon()
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        val coordinates = getCommonCoordinates(context)
        val task = getReplaceTask(coordinates, state, player)
        task.start();
    }

    private fun getReplaceTask(coordinates: List<BlockPos>, state: IBlockState, player: EntityPlayer): BlockBuildingTask {
        val toConsume = coordinates.count()
        val blocksConsumed = UtilPlayer.consumeBlocks(player, player.world, state, toConsume, false)
        val gooToConsume = toConsume - blocksConsumed
        val gooConsumed = UtilPlayer.consumeGoo(player, gooToConsume, false)

        val realBlocks = coordinates.subList(0, blocksConsumed).S
                .map { BlockBuildData(it, true) }
        val gooBlocks = coordinates.subList(blocksConsumed, blocksConsumed + gooConsumed).S
                .map { BlockBuildData(it, false) }

        val buildData = (realBlocks + gooBlocks).toList().shuffled()

        val buildTime = UtilTick.secondsToTicks(2f)
        val blocksPerTick = if (buildTime > buildData.size) 1 else buildData.size / buildTime
        return BlockBuildingTask(buildData, state, player, blocksPerTick, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getCommonCoordinates(context)
        val state = context.stack.get(selectPropertyWrapper.selectedBlock)
        BlockExchangeRendering.render(lastEvent, context.player, state, coordinates)
    }

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        event.drops.removeIf(event.harvester::addItemStackToInventory)
    }
}
