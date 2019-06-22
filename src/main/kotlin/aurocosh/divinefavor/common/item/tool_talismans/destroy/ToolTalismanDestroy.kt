package aurocosh.divinefavor.common.item.tool_talismans.destroy

import aurocosh.divinefavor.client.block_ovelay.BlockDestructionRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.init.Blocks
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
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean {
        if (!context.raycastValid)
            return false

        val pos = context.pos
        val playerPos = context.player.position
        return pos != playerPos && pos.distanceSq(playerPos) < hudDistanceSq
    }

    protected open fun getRenderCoordinates(context: TalismanContext) = getCommonCoordinates(context)
    protected open fun getFinalCoordinates(context: TalismanContext) = getCommonCoordinates(context).shuffled()
    protected open fun getCommonCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirOrReplacable(it) }

    override fun performActionServer(context: TalismanContext) {
        val coordinates = context.get(finalCoordinates)
        BlockPlacingTask(coordinates, Blocks.AIR.defaultState, context.player, 1).start()
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getRenderCoordinates(context)
        BlockDestructionRendering.render(lastEvent, context.player, coordinates)
    }

    override fun preProcess(context: TalismanContext): Boolean {
        if (!selectPropertyWrapper.preprocess(context))
            return false

        val (player, stack, world) = context.getCommon()
        val state = stack.get(selectPropertyWrapper.selectedBlock)

        val coordinates = getFinalCoordinates(context)
        val validCoordinates = UtilBlock.getBlocksForPlacement(player, world, state, coordinates)
        context.set(finalCoordinates, validCoordinates)
        return coordinates.isNotEmpty()
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int

    companion object {
        private const val hudDistance = 6
        const val hudDistanceSq = hudDistance * hudDistance
    }
}
