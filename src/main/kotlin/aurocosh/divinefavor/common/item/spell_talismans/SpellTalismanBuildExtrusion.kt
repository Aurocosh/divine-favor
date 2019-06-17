package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.ExtrusionCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
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

class SpellTalismanBuildExtrusion(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val surface: StackPropertyInt = propertyHandler.registerIntProperty("surface", 10, 1, 64)
    private val length: StackPropertyInt = propertyHandler.registerIntProperty("length", 6, 1, 32)

    private val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    override fun getFavorCost(itemStack: ItemStack): Int = favorCost * itemStack.get(length) * itemStack.get(surface)
    override fun raycastBlock(stack: ItemStack) = positionPropertyWrapper.shouldRaycastBlock(stack)
    override fun preProcess(context: TalismanContext): Boolean = selectPropertyWrapper.preprocess(context)

    override fun performActionServer(context: TalismanContext) {
        val (player, stack, world) = context.getCommon()
        val state = stack.get(selectedBlock)

        val coordinates = getCoordinates(context).shuffled()
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

    private fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val (length, surface) = stack.get(length, surface)
        val origin = positionPropertyWrapper.getPosition(context, context.pos)
        return coordinateGenerator.getCoordinates(context.pos, origin, world, context.facing, surface, length).filter(world::isAirOrReplacable)
    }

    companion object {
        private val coordinateGenerator: ExtrusionCoordinateGenerator = ExtrusionCoordinateGenerator()
    }
}
