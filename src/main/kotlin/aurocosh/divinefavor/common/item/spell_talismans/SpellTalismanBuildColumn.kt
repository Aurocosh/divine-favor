package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.client.block_ovelay.ToolRenders
import aurocosh.divinefavor.common.coordinate_generators.ColumnCoordinateGenerator
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyBlockPos
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyBool
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyIBlockState
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyInt
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: TalismanPropertyInt = propertyHandler.registerIntProperty("block_count", 24)
    private val selectedBlock: TalismanPropertyIBlockState = propertyHandler.registerBlockStateProperty("selectedBlock", Blocks.AIR.defaultState)
    private val lockPosition: TalismanPropertyBool = propertyHandler.registerBoolProperty("lockPosition", false, this::onPositionLock)
    private val lockedPosition: TalismanPropertyBlockPos = propertyHandler.registerBlockPosProperty("lockedPostion", BlockPos.ORIGIN)
//    private val lockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)

    override fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost * blockCount.getValue(itemStack);
    }

    override fun validate(context: TalismanContext): Boolean {
        if (context.player.isSneaking) {
            val state = context.world.getBlockState(context.pos)
            selectedBlock.setValue(context.stack, state)
            selectedBlock.syncToServer(state)
            return false
        }
        return true
    }

    override fun performActionServer(context: TalismanContext) {
        val count = blockCount.getValue(context.stack)
        val state = selectedBlock.getValue(context.stack)
        val itemStack = UtilBlock.getSilkDropIfPresent(context.world, state, context.player)
        val itemCount = UtilPlayer.countItems(itemStack, context.player)
        UtilPlayer.consumeItems(itemStack, context.player, count)

        val coordinates = coordinateGenerator.getCoordinates(context.pos, count).take(itemCount)
        for (blockPos in coordinates) {
            UtilBlock.replaceBlock(context.player, context.world, blockPos, state)
        }
    }

    @SideOnly(Side.CLIENT)
    override fun handleCustomRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val count = blockCount.getValue(context.stack)
        val state = selectedBlock.getValue(context.stack)
        val locked = lockPosition.getValue(context.stack)
        val lockedPos = lockedPosition.getValue(context.stack)

        val blockPos = if (locked) lockedPos else context.pos

        val coordinates = coordinateGenerator.getCoordinates(blockPos, count)
        BlockConstructionRendering.render(lastEvent, context.player, state, coordinates)
    }

    fun onPositionLock(stack: ItemStack) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return

        val pos = traceResult.blockPos
        lockedPosition.setValueAndSync(stack, pos)
    }

    companion object {
        private val coordinateGenerator: ColumnCoordinateGenerator = ColumnCoordinateGenerator()
    }
}
