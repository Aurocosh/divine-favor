package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockDestructionRendering
import aurocosh.divinefavor.common.coordinate_generators.CuboidCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanDestroyCuboid(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val up: StackPropertyInt = propertyHandler.registerIntProperty("up", 1, 0, 10)
    private val down: StackPropertyInt = propertyHandler.registerIntProperty("down", 1, 0, 10)
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 1, 0, 10)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 1, 0, 10)
    private val depth: StackPropertyInt = propertyHandler.registerIntProperty("depth", 3, 1, 20)
    private val fuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)

    private val positionPropertyWrapper = PositionPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val (left, right, up, down, depth) = itemStack.get(left, right, up, down, depth)
        return favorCost * getBlockCount(left, right, up, down, depth)
    }

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack) = positionPropertyWrapper.shouldRaycastBlock(stack)
    override fun preProcess(context: TalismanContext): Boolean = selectPropertyWrapper.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, _, world) = context.getCommon()
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)

        val state = Blocks.AIR.defaultState
        val blockCount = getBlockCount(left, right, up, down, depth)
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
        val coordinates = getCoordinates(context)
        BlockDestructionRendering.render(lastEvent, player, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (player, stack, world) = context.getCommon()
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)

        val blockCount = getBlockCount(left, right, up, down, depth)
        val count = Math.min(limit, blockCount)
        val blockPos = positionPropertyWrapper.getPosition(context)
        val directions = UtilPlayer.getRelativeDirections(player, context.facing)

        val coordinated = coordinateGenerator.getCoordinates(directions, blockPos, up, down, left, right, depth, count).S.filterNot(world::isAirOrReplacable)
        if (stack.get(fuzzy))
            return coordinated.toList()
        val state = stack.get(selectedBlock)
        return coordinated.filter(world::getBlockState, state::equals).toList()
    }

    private fun getBlockCount(left: Int, right: Int, up: Int, down: Int, depth: Int): Int {
        val width = left + 1 + right
        val height = up + 1 + down
        return width * height * depth
    }

    companion object {
        private val coordinateGenerator: CuboidCoordinateGenerator = CuboidCoordinateGenerator()
    }
}
