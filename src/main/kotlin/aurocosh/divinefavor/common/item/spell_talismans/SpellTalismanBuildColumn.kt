package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyHandler
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPositionPropertyHandler
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

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 24)
    private val shiftUp: StackPropertyInt = propertyHandler.registerIntProperty("shift_up", 1, -8, 8)

    private val lockPropertyHandler = LockPositionPropertyHandler(propertyHandler)
    private val isPosLocked: StackPropertyBool = lockPropertyHandler.isLockPosition
    private val lockedPosition: StackPropertyBlockPos = lockPropertyHandler.lockedPosition

    private val selectPropertyHandler = BlockSelectPropertyHandler(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * length.getValue(itemStack)
    override fun validateCastType(context: TalismanContext): Boolean = lockPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (blockCount, state) = stack.get(length, selectedBlock)

        val blocksToPlace = UtilPlayer.countBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, blocksToPlace).shuffled()

        UtilPlayer.consumeBlocks(player, world, state, coordinates.count())
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        isPosLocked.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        if (!lockPropertyHandler.shouldRender(context))
            return
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val blockPos = getOrigin(context.pos, stack)
        val count = Math.min(limit, stack.get(length))
        return coordinateGenerator.getCoordinates(blockPos, count).filter(world::isAirOrReplacable)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        val (shiftUp, locked) = stack.get(shiftUp, isPosLocked)
        val blockPos = if (!locked) pos else stack.get(lockedPosition)
        return blockPos.add(0, shiftUp, 0)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
