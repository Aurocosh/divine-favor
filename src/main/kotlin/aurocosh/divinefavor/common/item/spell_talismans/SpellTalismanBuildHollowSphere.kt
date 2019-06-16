package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.coordinate_generators.HollowSphereCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildHollowSphere(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val radius_internal: StackPropertyInt = propertyHandler.registerIntProperty("radius_internal", 3, 1, 10)
    private val radius_external: StackPropertyInt = propertyHandler.registerIntProperty("radius_external", 1, 1, 10)

    private val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    private val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)
    private val selectedBlock = selectPropertyWrapper.selectedBlock

    init {
        radius_internal.addChangeListener(this::onInternalChanged)
        radius_external.addChangeListener(this::onExternalChanged)
    }

    private fun onInternalChanged(stack: ItemStack, value: Int) {
        val external = stack.get(radius_external)
        if (external <= value)
            stack.set(radius_internal, external - 1, true)
    }

    private fun onExternalChanged(stack: ItemStack, value: Int) {
        val internal = stack.get(radius_internal)
        if (internal >= value)
            stack.set(radius_external, internal + 1, true)
    }

    private fun getRadiuses(itemStack: ItemStack): Pair<Int, Int> {
        val (radius_internal, radius_external) = itemStack.get(radius_internal, radius_external)
        if (radius_internal >= radius_external)
            return Pair(radius_external - 1, radius_external)
        return Pair(radius_internal, radius_external)
    }

    override fun getFavorCost(itemStack: ItemStack): Int {
        val (internal, external) = getRadiuses(itemStack)
        val volume = ((4 * Math.PI) / 3) * ((internal * internal * internal) - (external * external * external))
        return favorCost * volume.toInt()
    }

    override fun validateCastType(context: TalismanContext): Boolean = positionPropertyWrapper.validateCastType(context)
    override fun preprocess(context: TalismanContext): Boolean = selectPropertyWrapper.preprocess(context) && positionPropertyWrapper.preprocess(context)

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
        if (!positionPropertyWrapper.shouldRender(context))
            return
        val (player, stack) = context.getCommon()
        val state = stack.get(selectedBlock)
        val coordinates = getCoordinates(context)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    private fun getCoordinates(context: TalismanContext, limit: Int = Int.MAX_VALUE): List<BlockPos> {
        val (_, stack, world) = context.getCommon()
        val (internal, external) = getRadiuses(stack)
        val blockPos = positionPropertyWrapper.getPosition(context, context.pos)
        return coordinateGenerator.getCoordinates(blockPos, internal, external).filter(world::isAirOrReplacable)
    }

    companion object {
        private val coordinateGenerator: HollowSphereCoordinateGenerator = HollowSphereCoordinateGenerator()
    }
}