package aurocosh.divinefavor.common.custom_data.player

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFury
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncTemplateClient
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncWindLeash
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object LoginDataSyncer {
    @SubscribeEvent
    @JvmStatic
    fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent) {
        syncData(event.player)
    }

    @SubscribeEvent
    @JvmStatic
    fun onPlayerChangedDimension(event: PlayerEvent.PlayerChangedDimensionEvent) {
        syncData(event.player)
    }

    @SubscribeEvent
    @JvmStatic
    fun onPlayerChangedDimension(event: PlayerEvent.PlayerRespawnEvent) {
        syncData(event.player)
    }

    private fun syncData(eventPlayer: EntityPlayer){
        val player = eventPlayer as? EntityPlayerMP ?: return
        val handler = player.divinePlayerData
        MessageSyncAllSpiritData(handler.spiritData).sendTo(player)
        MessageSyncFury(handler.focusedFuryData.mobTypeId).sendTo(player)
        MessageSyncGrudge(handler.grudgeData.mobTypeId).sendTo(player)
        MessageSyncTemplateClient(handler.templateData.currentTemplate).sendTo(player)

        val livingDataHandler = player.divineLivingData
        MessageSyncWindLeash(livingDataHandler.windLeashData.vector).sendTo(player)
    }
}