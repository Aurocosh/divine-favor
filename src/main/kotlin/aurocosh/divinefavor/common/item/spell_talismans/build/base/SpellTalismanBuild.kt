package aurocosh.divinefavor.common.item.spell_talismans.build.base

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.block_operations.`do`.SimpleBuildOperation
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class SpellTalismanBuild(name: String, spirit: ModSpirit, favorCost: Int = ConfigGeneral.blockBuildingCost) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST_TRACE) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    protected abstract val positionPropertyWrapper: PositionPropertyWrapper
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: CastContext) = favorCost * context.get(finalCoordinates).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: CastContext): Boolean = positionPropertyWrapper.shouldRender(context)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    override fun preProcess(context: CastContext): Boolean {
        if (!selectPropertyWrapper.preprocess(context))
            return false
        val coordinates = getCommonCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.isNotEmpty()
    }

    protected open fun getCommonCoordinates(context: CastContext) = getCoordinates(context).filter { context.world.isAirOrReplacable(it) }

    override fun performActionServer(context: CastContext) {
        val (stack, player, world) = context.get(stackField, playerField, worldField)
        val coordinates = context.get(finalCoordinates)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        val buildOperation = SimpleBuildOperation(coordinates, state)
        buildOperation.perform(player, world)
        player.divinePlayerData.blockOperationsData.clearRedoActions()
    }

    override fun performActionClient(context: CastContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, value = false, sync = true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: CastContext, lastEvent: RenderWorldLastEvent) {
        val (player, stack) = context.getCommon()
        val coordinates = getCommonCoordinates(context)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        BlockConstructionRendering.render(lastEvent, player, state, coordinates)
    }

    protected abstract fun getCoordinates(context: CastContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int
}
