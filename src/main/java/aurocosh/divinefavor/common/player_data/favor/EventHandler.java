package aurocosh.divinefavor.common.player_data.favor;

import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.client.MessageDataSync;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            IFavorHandler favorHandler = event.player.getCapability(CAPABILITY_FAVOR, null);
            if (favorHandler == null)
                return;
            MessageDataSync message = new MessageDataSync(favorHandler);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) event.player);
        }
    }
}