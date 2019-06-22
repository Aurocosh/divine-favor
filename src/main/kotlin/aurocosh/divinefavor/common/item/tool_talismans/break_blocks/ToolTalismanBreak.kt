package aurocosh.divinefavor.common.item.tool_talismans.break_blocks

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.tasks.BlockBreakingTask
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

abstract class ToolTalismanBreak(name: String, spirit: ModSpirit) : ItemToolTalisman(name, spirit, ConfigGeneral.blockBreakingCost) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", true)
    val doNotBreakBelow = propertyHandler.registerBoolProperty("do_not_break_below", false)
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = true
    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size
    override fun preValidate(context: TalismanContext) = context.player.isSneaking || super.preValidate(context)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean {
        if (!context.raycastValid)
            return false

        val pos = context.pos
        val playerPos = context.player.position
        return pos != playerPos && pos.distanceSq(playerPos) < hudDistanceSq
    }

    override fun preProcess(context: TalismanContext): Boolean {
        if (!selectPropertyWrapper.preprocess(context))
            return false
        val coordinates = getFinalCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.isNotEmpty()
    }

    protected open fun getCommonCoordinates(context: TalismanContext): List<BlockPos> {
        val spellPick = context.containerStack.item as? ItemSpellPick ?: return emptyList()
        val world = context.world
        val coordinates = getCoordinates(context)
        val y = context.player.position.y
        val preFiltered = if (context.stack.get(doNotBreakBelow)) coordinates.filter { it.y >= y } else coordinates
        return preFiltered.filterNot(world::isAirBlock).filter(world::getBlockState) { spellPick.canHarvestBlock(it, context.containerStack) }.toList()
    }

    protected open fun getRenderCoordinates(context: TalismanContext): List<BlockPos> = getCommonCoordinates(context)
    protected open fun getFinalCoordinates(context: TalismanContext): List<BlockPos> = getCommonCoordinates(context).shuffled()

    override fun performActionServer(context: TalismanContext) {
        val coordinates = context.get(finalCoordinates)
        BlockBreakingTask(coordinates, context.player, context.stack, 1).start()
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getRenderCoordinates(context)
        BlockHighlightRendering.render(lastEvent, context.player, coordinates, renderColor)
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int

    companion object {
        val renderColor = Color3f(0.2f, 0.6f, 0f)
        private const val hudDistance = 6
        const val hudDistanceSq = hudDistance * hudDistance
    }
}
