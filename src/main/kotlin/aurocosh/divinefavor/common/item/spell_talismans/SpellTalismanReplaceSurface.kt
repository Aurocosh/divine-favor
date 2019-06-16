package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.coordinate_generators.SurfaceCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
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

class SpellTalismanReplaceSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    private val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)

    private val lockPropertyHandler = LockPositionPropertyWrapper(propertyHandler)
    private val isPosLocked: StackPropertyBool = lockPropertyHandler.isLockPosition
    private val lockedPosition: StackPropertyBlockPos = lockPropertyHandler.lockedPosition

    private val selectPropertyHandler = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * blockCount.getValue(itemStack)
    override fun validateCastType(context: TalismanContext): Boolean = lockPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (blockCount, state) = stack.get(blockCount, selectedBlock)

        val count = UtilPlayer.countBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, count).filterNot(world::isAirOrReplacable).shuffled()

        UtilPlayer.consumeBlocks(player, world, state, coordinates.size)
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        isPosLocked.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        if (!lockPropertyHandler.shouldRender(context))
            return
        val state = context.stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockExchangeRendering.render(lastEvent, context.player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val state = context.stack.get(selectedBlock)
        if (state == context.world.getBlockState(context.pos))
            return emptyList()

        val (count, fuzzy) = context.stack.get(blockCount, isFuzzy)
        val blockPos = getOrigin(context.pos, context.stack)
        return coordinateGenerator.getCoordinates(blockPos, Math.min(count, limit), context.world, fuzzy)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        return if (!stack.get(isPosLocked)) pos else stack.get(lockedPosition)
    }

    companion object {
        private val coordinateGenerator: SurfaceCoordinateGenerator = SurfaceCoordinateGenerator()
    }
}
