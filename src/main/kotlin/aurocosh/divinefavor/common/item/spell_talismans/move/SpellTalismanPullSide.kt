package aurocosh.divinefavor.common.item.spell_talismans.move

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.common.coordinate_generators.generateSideCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.BlockSelectPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*
import javax.vecmath.Color3f

open class SpellTalismanPullSide(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    protected val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    protected val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    protected val selectPropertyWrapper = BlockSelectPropertyWrapper(propertyHandler)

    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost * getBlockCount(itemStack)
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size

    protected fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)
    private fun getRenderCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirBlock(it) }
    protected fun getFinalCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirBlock(it) }.shuffled()

    override fun performActionServer(context: TalismanContext) {
        val (player, world, facing) = context.get(playerField, worldField, facingField)
        val coordinates = getFinalCoordinates(context)
        coordinates.forEach { UtilBlock.moveBlock(player, world, it, facing) }
    }

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = context.raycastValid

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getRenderCoordinates(context)
        BlockHighlightRendering.render(lastEvent, context.player, coordinates, color)
    }

    open fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (stack, world, facing, pos) = context.get(stackField, worldField, facingField, posField)
        val fuzzy = stack.get(isFuzzy)

        val count = getBlockCount(stack)
        val state = world.getBlockState(pos)

        return cachedContainer.getValue(facing, pos, count, fuzzy) {
            val predicate: (IBlockState) -> Boolean = { fuzzy || it == state }
            generateSideCoordinates(pos, count, world, facing, predicate)
        }
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

    companion object {
        val color = Color3f(0f, 0.2f, 0.5f)
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
