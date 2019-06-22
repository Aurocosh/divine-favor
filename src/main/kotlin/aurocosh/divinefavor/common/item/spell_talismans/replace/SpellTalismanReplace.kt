package aurocosh.divinefavor.common.item.spell_talismans.replace

import aurocosh.divinefavor.client.block_ovelay.BlockExchangeRendering
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuild
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.tasks.BlockReplacingTask
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

abstract class SpellTalismanReplace(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuild(name, spirit, favorCost, options), IBlockCatcher {
    protected val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 6, 1, 64)
    protected val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", false)
    override val positionPropertyWrapper: PositionPropertyWrapper = PositionPropertyWrapper(propertyHandler)

    override fun getRenderCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirBlock(it) }
    override fun getFinalCoordinates(context: TalismanContext) = getCoordinates(context).filterNot { context.world.isAirBlock(it) }.shuffled()

    override fun performActionServer(context: TalismanContext) {
        val (player, stack) = context.getCommon()
        val state = stack.get(selectPropertyWrapper.selectedBlock)
        val coordinates = getFinalCoordinates(context)
        BlockReplacingTask(coordinates, state, player, 1).start()
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val coordinates = getRenderCoordinates(context)
        val state = context.stack.get(selectPropertyWrapper.selectedBlock)
        BlockExchangeRendering.render(lastEvent, context.player, state, coordinates)
    }

    override fun catch(player: EntityPlayer, stack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        event.drops.removeIf(player::addItemStackToInventory)
    }
}
