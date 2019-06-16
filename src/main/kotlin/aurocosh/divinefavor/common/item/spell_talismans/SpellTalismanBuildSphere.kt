package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.SphereCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.LockPositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
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

class SpellTalismanBuildSphere(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val radius: StackPropertyInt = propertyHandler.registerIntProperty("radius", 2, 1, 10)
    private val shiftUp: StackPropertyInt = propertyHandler.registerIntProperty("shift_up", 1, -8, 8)

    private val lockPositionPropertyHandler = LockPositionPropertyWrapper(propertyHandler)

    private val selectPropertyHandler = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyHandler.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int {
        val radius = itemStack.get(radius)
        val volume = ((4 * Math.PI) / 3) * (radius * radius * radius)
        return favorCost * volume.toInt()
    }

    override fun validateCastType(context: TalismanContext): Boolean = lockPositionPropertyHandler.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyHandler.preprocess(context) && lockPositionPropertyHandler.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()

        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        val blocksToPlace = UtilPlayer.countBlocks(player, world, state, coordinates.count())

        val finalCoordinates = coordinates.take(blocksToPlace).shuffled()
        UtilPlayer.consumeBlocks(player, world, state, finalCoordinates.count())
        BlockPlacingTask(finalCoordinates, state, player, 1).start()
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

    private fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val radius= stack.get(radius)
        val blockPos = getOrigin(context.pos, stack)
        return coordinateGenerator.getCoordinates(blockPos, radius).filter(world::isAirOrReplacable)
    }

    private fun getOrigin(pos: BlockPos, stack: ItemStack): BlockPos {
        val blockPos = lockPositionPropertyHandler.getPosition(stack, pos)
        return blockPos.add(0, stack.get(shiftUp), 0)
    }

    companion object {
        private val coordinateGenerator: SphereCoordinateGenerator = SphereCoordinateGenerator()
    }
}
