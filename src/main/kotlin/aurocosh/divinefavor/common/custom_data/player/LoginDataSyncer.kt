package aurocosh.divinefavor.common.custom_data.player

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFury
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncWindLeash
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class LoginDataSyncer {
    companion object {
        @SubscribeEvent
        fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent) {
            if (event.player !is EntityPlayerMP)
                return

            val handler = event.player.divinePlayerData
            MessageSyncAllSpiritData(handler.spiritData).sendTo(event.player)
            MessageSyncFury(handler.focusedFuryData.mobTypeId).sendTo(event.player)
            MessageSyncGrudge(handler.grudgeData.mobTypeId).sendTo(event.player)

            val livingDataHandler = event.player.divineLivingData
            MessageSyncWindLeash(livingDataHandler.windLeashData.vector).sendTo(event.player)
        }
    }
}