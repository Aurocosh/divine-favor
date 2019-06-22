package aurocosh.divinefavor.common.item.spell_talismans.build.base

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class SpellTalismanBuild(name: String, spirit: ModSpirit, favorCost: Int = ConfigGeneral.blockBuildingCost) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST_TRACE) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    protected abstract val positionPropertyWrapper : PositionPropertyWrapper
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    override fun preProcess(context: TalismanContext): Boolean {
        if(!selectPropertyWrapper.preprocess(context))
            return false

        val (player, stack, world) = context.getCommon()
        val state = stack.get(selectPropertyWrapper.selectedBlock)

        val coordinates = getFinalCoordinates(context)
        val validCoordinates = UtilBlock.getBlocksForPlacement(player, world, state, coordinates)
        context.set(finalCoordinates, validCoordinates)
        return coordinates.isNotEmpty()
    }

    protected open fun getRenderCoordinates(context: TalismanContext) = getCoordinates(context).filter{context.world.isAirOrReplacable(it)}
    protected open fun getFinalCoordinates(context: TalismanContext) = getCoordinates(context).filter{context.world.isAirOrReplacable(it)}.shuffled()

    override fun performActionServer(context: TalismanContext) {
        val coordinates = context.get(finalCoordinates)
        val state = context.stack.get(selectPropertyWrapper.selectedBlock)
        BlockPlacingTask(coordinates, state, context.player, 1).start()
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val (player, stack) = context.getCommon()
        val coordinates = getRenderCoordinates(context)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int
}
