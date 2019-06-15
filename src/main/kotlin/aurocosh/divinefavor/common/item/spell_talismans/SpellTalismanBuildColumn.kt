package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.S
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

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 24)
    private val shiftX: StackPropertyInt = propertyHandler.registerIntProperty("shift_x", 0, -8, 8)
    private val shiftY: StackPropertyInt = propertyHandler.registerIntProperty("shift_y", 0, -8, 8)
    private val shiftZ: StackPropertyInt = propertyHandler.registerIntProperty("shift_z", 0, -8, 8)
    private val isPosLocked: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    private val selectedBlock: StackPropertyIBlockState = propertyHandler.registerBlockStateProperty("selected_block", Blocks.AIR.defaultState)
    private val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)

    init {
        isPosLocked.addChangeListener(this::onPositionLock)
    }

    override fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost * blockCount.getValue(itemStack);
    }

    override fun preprocess(context: TalismanContext): Boolean {
        val stack = context.stack
        if (context.player.isSneaking) {
            val state = context.world.getBlockState(context.pos)
            selectedBlock.setValue(stack, state, true)
            return false
        }
        if (stack.get(isPosLocked))
            return true
        return context.valid
    }

    override fun performActionServer(context: TalismanContext) {
        val stack = context.stack
        val locked = stack.get(isPosLocked)
        if (!locked && !context.valid)
            return

        val (count, state) = stack.get(blockCount, selectedBlock)
        val itemStack = UtilBlock.getSilkDropIfPresent(context.world, state, context.player)
        val itemCount = UtilPlayer.countItems(itemStack, context.player)
        UtilPlayer.consumeItems(itemStack, context.player, count)

        val blockPos = getOrigin(locked, context.pos, stack)
        val coordinates = coordinateGenerator.getCoordinates(blockPos, count).take(itemCount).shuffled()

        BlockPlacingTask(coordinates, state, context.player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        isPosLocked.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val stack = context.stack
        val locked = stack.get(isPosLocked)
        if (!locked && !context.valid)
            return

        val blockPos = getOrigin(locked, context.pos, stack)
        val (count, state) = stack.get(blockCount, selectedBlock, lockedPosition)
        val coordinates = coordinateGenerator.getCoordinates(blockPos, count)
        BlockConstructionRendering.render(lastEvent, context.player, state, coordinates)
    }

    private fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return

        val pos = traceResult.blockPos
        lockedPosition.setValue(stack, pos, true)
    }

    private fun getOrigin(locked: Boolean, pos: BlockPos, stack: ItemStack): BlockPos {
        val (shiftX, shiftY, shiftZ) = stack.get(shiftX, shiftY, shiftZ)
        val blockPos = if (!locked) pos else stack.get(lockedPosition)
        return blockPos.add(shiftX, shiftY, shiftZ)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
