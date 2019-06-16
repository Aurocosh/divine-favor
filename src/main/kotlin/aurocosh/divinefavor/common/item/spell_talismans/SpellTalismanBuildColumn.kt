package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyHandler
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyIBlockState
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 24)
    private val shiftForward: StackPropertyInt = propertyHandler.registerIntProperty("shift_forward", 0, -8, 8)
    private val shiftUp: StackPropertyInt = propertyHandler.registerIntProperty("shift_up", 0, -8, 8)
    private val shiftRight: StackPropertyInt = propertyHandler.registerIntProperty("shift_right", 0, -8, 8)

    private val lockPropertyHandler = LockPropertyHandler(propertyHandler)
    private val isPosLocked: StackPropertyBool = lockPropertyHandler.isPosLocked
    private val lockedPosition: StackPropertyBlockPos = lockPropertyHandler.lockedPosition

    private val selectPropertyHandler = BlockSelectPropertyHandler(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * blockCount.getValue(itemStack)
    override fun validateCastType(context: TalismanContext): Boolean = lockPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (blockCount, state) = stack.get(blockCount, selectedBlock)

        val count = UtilPlayer.consumeBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, count)

        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        isPosLocked.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        if(!lockPropertyHandler.shouldRender(context))
            return
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (player, stack) = context.getCommon()
        val blockPos = getOrigin(player, context.pos, stack)
        val count = Math.min(limit, stack.get(blockCount))
        return coordinateGenerator.getCoordinates(blockPos, count)
    }

    private fun getOrigin(player: EntityPlayer, pos: BlockPos, stack: ItemStack): BlockPos {
        val (shiftForward, shiftUp, shiftRight, locked) = stack.get(shiftForward, shiftUp, shiftRight, isPosLocked)
        val blockPos = if (!locked) pos else stack.get(lockedPosition)
        val shift = UtilPlayer.getShift(player, shiftForward, shiftUp, shiftRight)
        return blockPos.add(shift)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
