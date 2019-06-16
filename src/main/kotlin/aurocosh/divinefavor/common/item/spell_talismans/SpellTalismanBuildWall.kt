package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.coordinate_generators.WallCoordinateGenerator
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

class SpellTalismanBuildWall(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val up: StackPropertyInt = propertyHandler.registerIntProperty("up", 3, 0, 10)
    private val down: StackPropertyInt = propertyHandler.registerIntProperty("down", 0, 0, 10)
    private val onTop: StackPropertyBool = propertyHandler.registerBoolProperty("on_top", true)

    private val lockPropertyHandler = LockPropertyHandler(propertyHandler)
    private val isPosLocked: StackPropertyBool = lockPropertyHandler.isPosLocked
    private val lockedPosition: StackPropertyBlockPos = lockPropertyHandler.lockedPosition

    private val selectPropertyHandler = BlockSelectPropertyHandler(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val (left, right, up, down) = itemStack.get(left, right, up, down)
        return favorCost * getBlockCount(left, right, up, down)
    }

    override fun validateCastType(context: TalismanContext): Boolean = lockPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (left, right, up, down) = context.stack.get(left, right, up, down)
        val blockCount = getBlockCount(left, right, up, down)
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
        val (left, right, up, down) = context.stack.get(left, right, up, down)

        val blockPos = getOrigin(context.pos, stack)
        val blockCount = getBlockCount(left, right, up, down)
        val count = Math.min(limit, blockCount)
        return coordinateGenerator.getCoordinates(player, blockPos, up, down, left, right, count)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        val blockPos = if (!stack.get(isPosLocked)) pos else stack.get(lockedPosition)
        return if (stack.get(onTop)) blockPos.up() else blockPos
    }

    private fun getBlockCount(left: Int, right: Int, up: Int, down: Int): Int {
        val width = left + 1 + right
        val height = up + 1 + down
        return width * height
    }

    companion object {
        private val coordinateGenerator: WallCoordinateGenerator = WallCoordinateGenerator()
    }
}
