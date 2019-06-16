package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.WallCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RelativePositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RotationPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildHorizontalLineRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 2, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 2, 0, 10)

    private val positionPropertyWrapper = RelativePositionPropertyWrapper(propertyHandler)
    private val rotationPropertyWrapper = RotationPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val (left, right) = itemStack.get(left, right)
        return favorCost * getBlockCount(left, right)
    }

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val (left, right) = context.stack.get(left, right)

        val state = stack.get(selectedBlock)
        val blockCount = getBlockCount(left, right)
        val coordinates = getCoordinates(context, blockCount).shuffled()
        val finalCoordinates = UtilBlock.getBlocksForPlacement(player, world, state, coordinates)
        BlockPlacingTask(finalCoordinates, state, player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (player, stack, world) = context.getCommon()
        val (left, right) = context.stack.get(left, right)

        val blockPos = positionPropertyWrapper.getPosition(context)
        val blockCount = getBlockCount(left, right)
        val count = Math.min(limit, blockCount)
        val facing = rotationPropertyWrapper.getRotation(stack, player.horizontalFacing)

        return coordinateGenerator.getCoordinates(facing, blockPos, 1, left, right, count).filter(world::isAirOrReplacable)
    }

    private fun getBlockCount(left: Int, right: Int): Int {
        return left + 1 + right
    }

    companion object {
        private val coordinateGenerator: WallCoordinateGenerator = WallCoordinateGenerator()
    }
}
