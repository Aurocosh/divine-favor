package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.coordinate_generators.SurfaceCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyIBlockState
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanReplaceSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    private val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    private val isPosLocked: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    private val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)
    private val selectedBlock: StackPropertyIBlockState = propertyHandler.registerBlockStateProperty("selected_block", Blocks.AIR.defaultState)

    init {
        isPosLocked.addChangeListener(this::onPositionLock)
    }

    override fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost * blockCount.getValue(itemStack);
    }

    override fun validateCastType(context: TalismanContext): Boolean {
        if (context.castType == CastType.UseCast)
            return true
        if (context.castType == CastType.RightCast)
            return context.stack.get(isPosLocked) || context.valid
        return false
    }

    override fun preprocess(context: TalismanContext): Boolean {
        if (context.player.isSneaking) {
            val state = context.world.getBlockState(context.pos)
            selectedBlock.setValue(context.stack, state, context.world.isRemote)
            return false
        }
        if (context.stack.get(isPosLocked))
            return true
        return context.valid
    }

    override fun performActionServer(context: TalismanContext) {
        val (player, world, stack) = context.getCommon()
        val (blockCount, state) = stack.get(blockCount, selectedBlock)

        val itemStack = UtilBlock.getSilkDropIfPresent(world, state, player)
        val itemCount = UtilPlayer.countItems(itemStack, player)
        val toConsume = Math.min(blockCount, itemCount)
        UtilPlayer.consumeItems(itemStack, player, toConsume)

        val coordinates = getCoordinates(context, toConsume).shuffled()
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        isPosLocked.setValue(context.stack, false, true)
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
        val blockPos = getOrigin(context.pos, context.stack)
        return coordinateGenerator.getCoordinates(blockPos, Math.min(count, limit), context.world, fuzzy)
    }

    private fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedPosition.setValue(stack, traceResult.blockPos, true)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        return if (!stack.get(isPosLocked)) pos else stack.get(lockedPosition)
    }

    companion object {
        private val coordinateGenerator: SurfaceCoordinateGenerator = SurfaceCoordinateGenerator()
    }
}
