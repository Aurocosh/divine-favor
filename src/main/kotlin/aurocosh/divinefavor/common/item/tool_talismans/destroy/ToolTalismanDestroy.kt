package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.common.block_operations.`do`.DestructionOperation
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ToolTalismanDestroy(name: String, spirit: ModSpirit) : ItemToolTalisman(name, spirit, ConfigGeneral.blockDestructionCost) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    protected val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = true
    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: CastContext) = favorCost * context.get(finalCoordinates).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: CastContext): Boolean {
        if (!context.raycastValid)
            return false

        val pos = context.pos
        val playerPos = context.player.position
        return pos != playerPos && pos.distanceSq(playerPos) < hudDistanceSq
    }

    protected open fun getCommonCoordinates(context: CastContext) = getCoordinates(context).filterNot { context.world.isAirOrReplacable(it) }

    override fun performActionServer(context: CastContext) {
        val (player, world) = context.get(playerField, worldField)
        val coordinates = context.get(finalCoordinates)
        val destructionOperation = DestructionOperation(coordinates)
        destructionOperation.perform(player, world)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: CastContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getCommonCoordinates(context)
        BlockHighlightRendering.render(lastEvent, context.player, coordinates)
    }

    override fun preValidate(context: CastContext): Boolean {
        return context.player.isSneaking || super.preValidate(context)
    }

    override fun preProcess(context: CastContext): Boolean {
        if (!selectPropertyWrapper.preprocess(context))
            return false

        val coordinates = getCommonCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.isNotEmpty()
    }

    protected abstract fun getCoordinates(context: CastContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int

    companion object {
        private const val hudDistance = 6
        const val hudDistanceSq = hudDistance * hudDistance
    }
}
