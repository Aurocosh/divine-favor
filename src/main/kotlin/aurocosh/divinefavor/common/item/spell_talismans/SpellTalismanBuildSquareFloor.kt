package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.FloorCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyHandler
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
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

class SpellTalismanBuildSquareFloor(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)
    private val shiftUp: StackPropertyInt = propertyHandler.registerIntProperty("shift_up", 1, -8, 8)

    private val lockPropertyHandler = LockPropertyHandler(propertyHandler)
    private val isPosLocked: StackPropertyBool = lockPropertyHandler.isPosLocked
    private val lockedPosition: StackPropertyBlockPos = lockPropertyHandler.lockedPosition

    private val selectPropertyHandler = BlockSelectPropertyHandler(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val radius = itemStack.get(radius) - 1
        return favorCost * getBlockCount(radius)
    }

    override fun validateCastType(context: TalismanContext): Boolean = lockPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val radius = stack.get(radius) - 1
        val blockCount = getBlockCount(radius)
        val state = stack.get(selectedBlock)

        val count = UtilPlayer.consumeBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, count).shuffled()
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
        val (player, stack) = context.getCommon()
        val radius = stack.get(radius) - 1

        val blockPos = getOrigin(context.pos, stack)
        val blockCount = getBlockCount(radius)
        val count = Math.min(limit, blockCount)
        return coordinateGenerator.getCoordinates(player, blockPos, radius, radius, radius, radius, count)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        val blockPos = if (!stack.get(isPosLocked)) pos else stack.get(lockedPosition)
        val shiftY = stack.get(shiftUp)
        return blockPos.add(0, shiftY, 0)
    }

    private fun getBlockCount(radius: Int): Int {
        val width = radius + 1 + radius
        return width * width
    }

    companion object {
        private val coordinateGenerator: FloorCoordinateGenerator = FloorCoordinateGenerator()
    }
}
