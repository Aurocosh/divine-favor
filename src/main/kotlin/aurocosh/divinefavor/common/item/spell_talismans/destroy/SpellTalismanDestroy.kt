package aurocosh.divinefavor.common.item.spell_talismans.destroy

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.common.block_operations.`do`.DestructionOperation
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionFacingPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class SpellTalismanDestroy(name: String, spirit: ModSpirit) : ItemSpellTalisman(name, spirit, ConfigGeneral.blockDestructionCost, SpellOptions.TRACE_ONLY_CAST) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    protected val positionPropertyWrapper = PositionFacingPropertyWrapper(propertyHandler)
    protected val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size


    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    protected open fun getCommonCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirOrReplacable(it) }

    override fun performActionServer(context: TalismanContext) {
        val (player, world) = context.get(playerField, worldField)
        val coordinates = context.get(finalCoordinates)
        val destructionOperation = DestructionOperation(coordinates)
        destructionOperation.perform(player, world)
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, value = false, sync = true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getCommonCoordinates(context)
        BlockHighlightRendering.render(lastEvent, context.player, coordinates)
    }

    override fun preValidate(context: TalismanContext): Boolean {
        return context.player.isSneaking || super.preValidate(context)
    }

    override fun preProcess(context: TalismanContext): Boolean {
        if (!selectPropertyWrapper.preprocess(context))
            return false

        val coordinates = getCommonCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.isNotEmpty()
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int
}
