package aurocosh.divinefavor.common.custom_data.player;

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFury;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class LoginDataSyncer {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;

        IPlayerDataHandler handler = PlayerData.get(event.player);
        new MessageSyncAllSpiritData(handler.getSpiritData()).sendTo(event.player);
        new MessageSyncFury(handler.getFocusedFuryData().getMobTypeId()).sendTo(event.player);
        new MessageSyncGrudge(handler.getGrudgeData().getMobTypeId()).sendTo(event.player);
    }
}