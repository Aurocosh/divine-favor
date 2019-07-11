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
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.undo.UndoBuild
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilTick
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
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalCoordinates).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

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

    protected open fun getRenderCoordinates(context: TalismanContext) = getCoordinates(context).filter { context.world.isAirOrReplacable(it) }
    protected open fun getFinalCoordinates(context: TalismanContext) = getCoordinates(context).filter { context.world.isAirOrReplacable(it) }.shuffled()

    override fun performActionServer(context: TalismanContext) {
        val (stack, player) = context.get(stackField, playerField)
        val coordinates = context.get(finalCoordinates)
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        val buildTime = UtilTick.secondsToTicks(2f)
        val blocksPerTick = if(buildTime > coordinates.size) 1 else coordinates.size / buildTime
        val blockPlacingTask = BlockPlacingTask(coordinates, state, player, blocksPerTick)
        blockPlacingTask.addFinishAction {
            val undoBuild = UndoBuild(coordinates)
            player.divinePlayerData.undoData.addAction(undoBuild)
        }
        blockPlacingTask.start()
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, value = false, sync = true)
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
