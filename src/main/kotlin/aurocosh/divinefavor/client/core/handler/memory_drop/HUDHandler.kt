package aurocosh.divinefavor.client.core.handler.memory_drop

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.BlockTemplateRendering
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.ItemMemoryDrop
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun renderWorldLastEvent(lastEvent: RenderWorldLastEvent) {
        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryDrop } ?: return
        val stack = player.getHeldItem(hand)

        if(!stack.isPropertySet(ItemMemoryDrop.uuid))
            return

        val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
                ?: return

        val uuid = stack.get(ItemMemoryDrop.uuid)
        BlockTemplateRendering.render(lastEvent, player, uuid, traceResult.blockPos)
    }
}