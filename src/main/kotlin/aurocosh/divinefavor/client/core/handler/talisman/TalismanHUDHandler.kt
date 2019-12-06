package aurocosh.divinefavor.client.core.handler.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object TalismanHUDHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun renderWorldLastEvent(lastEvent: RenderWorldLastEvent) {
        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ISelectedStackProvider } ?: return
        val containerStack = player.getHeldItem(hand)
        val stackProvider = containerStack.item as ISelectedStackProvider

        val storedStack = stackProvider.getSelectedStack(containerStack)
        if(storedStack.isEmpty)
            return

        val talisman = storedStack.item as? ItemTalisman ?: return
        val context = CastContextGenerator.generic(player, hand, storedStack, containerStack)
        if (talisman.shouldRender(context))
            talisman.handleRendering(context, lastEvent)
    }
}