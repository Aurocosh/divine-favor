package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.coordinate_generators.FloodFillCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanReplaceBlocks(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    private val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)

    private val positionPropertyWrapper = PositionPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * blockCount.getValue(itemStack)
    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack) = positionPropertyWrapper.shouldRaycastBlock(stack)
    override fun preProcess(context: TalismanContext): Boolean = selectPropertyWrapper.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (blockCount, state) = stack.get(blockCount, selectedBlock)

        val count = UtilPlayer.countBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, count).filterNot(world::isAirOrReplacable).shuffled()

        UtilPlayer.consumeBlocks(player, world, state, coordinates.size)
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val state = context.stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockExchangeRendering.render(lastEvent, context.player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val state = context.stack.get(selectedBlock)
        if (state == context.world.getBlockState(context.pos))
            return emptyList()

        val (count, fuzzy) = context.stack.get(blockCount, isFuzzy)
        val blockPos = positionPropertyWrapper.getPosition(context)
        return coordinateGenerator.getCoordinates(blockPos, Math.min(count, limit), context.world, fuzzy)
    }

    companion object {
        private val coordinateGenerator: FloodFillCoordinateGenerator = FloodFillCoordinateGenerator()
    }
}
