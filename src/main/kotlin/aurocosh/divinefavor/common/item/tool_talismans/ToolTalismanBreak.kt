package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockDestructionRendering
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockBreakingTask
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

abstract class ToolTalismanBreak(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    val doNotBreakBelow = propertyHandler.registerBoolProperty("do_not_break_below", false)
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

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

    override fun raycastBlock(stack: ItemStack, castType: CastType) = true

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
        val preFiltered = if (context.stack.get(doNotBreakBelow)) coordinates.S.filter { it.y >= y } else coordinates.S
        return preFiltered.filterNot(world::isAirBlock).filter(world::getBlockState, spellPick::canHarvestBlock).toList()
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
        BlockDestructionRendering.render(lastEvent, context.player, coordinates, Color3f(0.5f, 0.2f, 0f))
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>
    protected abstract fun getBlockCount(stack: ItemStack): Int

    companion object {
        const val hudDistance = 6
        const val hudDistanceSq = hudDistance * hudDistance
    }
}
