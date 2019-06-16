package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockFacingPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildFromSurface(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 24)
    private val shift: StackPropertyInt = propertyHandler.registerIntProperty("shift", 1, -8, 8)

    private val positionPropertyWrapper = LockPositionPropertyWrapper(propertyHandler)
    private val facingPropertyWrapper = LockFacingPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * itemStack.get(length)
    override fun validateCastType(context: TalismanContext): Boolean = positionPropertyWrapper.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyWrapper.preprocess(context) && positionPropertyWrapper.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (blockCount, state) = stack.get(length, selectedBlock)

        val blocksToPlace = UtilPlayer.countBlocks(player, world, state, blockCount)
        val coordinates = getCoordinates(context, blocksToPlace).shuffled()

        UtilPlayer.consumeBlocks(player, world, state, coordinates.count())
        BlockPlacingTask(coordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        if (!positionPropertyWrapper.shouldRender(context))
            return
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val count = Math.min(limit, stack.get(length))

        val blockPos = getOrigin(context)
        val facing = facingPropertyWrapper.getFacing(stack, context.facing)
        return coordinateGenerator.getCoordinates(blockPos, facing, count).filter(world::isAirOrReplacable)
    }

    private fun getOrigin(context: TalismanContext): BlockPos {
        val stack = context.stack


        val blockPos = positionPropertyWrapper.getPosition(stack, context.pos)
        val facing = facingPropertyWrapper.getFacing(stack, context.facing)
        val shiftValue = stack.get(shift)
        val shiftVec = facing.directionVec.toBlockPos().scale(shiftValue)
        return blockPos.add(shiftVec)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
