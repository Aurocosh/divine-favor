package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.FloorCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyHandler
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPositionPropertyHandler
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockRotationPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyEnumFacing
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildFloor(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)
    private val front: StackPropertyInt = propertyHandler.registerIntProperty("front", 3, 0, 10)
    private val back: StackPropertyInt = propertyHandler.registerIntProperty("back", 0, 0, 10)
    private val shiftUp: StackPropertyInt = propertyHandler.registerIntProperty("shift_up", 1, -8, 8)

    private val lockPositionPropertyHandler = LockPositionPropertyHandler(propertyHandler)
    private val rotationPropertyHandler = LockRotationPropertyHandler(propertyHandler)

    private val selectPropertyHandler = BlockSelectPropertyHandler(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val (left, right, front, back) = itemStack.get(left, right, front, back)
        return favorCost * getBlockCount(left, right, front, back)
    }

    override fun validateCastType(context: TalismanContext): Boolean = lockPositionPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPositionPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (left, right, front, back, state) = stack.get(left, right, front, back, selectedBlock)

        val blockCount = getBlockCount(left, right, front, back)
        val blocksToPlace = UtilPlayer.countBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, blocksToPlace).shuffled()

        UtilPlayer.consumeBlocks(player, world, state, coordinates.count())
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        lockPositionPropertyHandler.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        if (!lockPositionPropertyHandler.shouldRender(context))
            return
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (player, stack, world) = context.getCommon()
        val (left, right, up, down) = context.stack.get(left, right, front, back)

        val blockPos = getOrigin(context.pos, stack)
        val blockCount = getBlockCount(left, right, up, down)
        val count = Math.min(limit, blockCount)
        val facing = rotationPropertyHandler.getRotation(stack, player.horizontalFacing)

        return coordinateGenerator.getCoordinates(facing, blockPos, up, down, left, right, count).filter(world::isAirOrReplacable)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        val blockPos = lockPositionPropertyHandler.getPosition(stack, pos)
        val shiftY = stack.get(shiftUp)
        return blockPos.add(0, shiftY, 0)
    }

    private fun getBlockCount(left: Int, right: Int, front: Int, back: Int): Int {
        val width = left + 1 + right
        val thickness = front + 1 + back
        return width * thickness
    }

    companion object {
        private val coordinateGenerator: FloorCoordinateGenerator = FloorCoordinateGenerator()
    }
}
