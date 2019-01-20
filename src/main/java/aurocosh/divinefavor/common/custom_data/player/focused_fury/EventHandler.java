package aurocosh.divinefavor.common.custom_data.player.focused_fury;

import aurocosh.divinefavor.common.network.message.client.MessageSyncFury;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static aurocosh.divinefavor.common.custom_data.player.focused_fury.FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;

        IFocusedFuryHandler furyHandler = event.player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;
        MessageSyncFury.sync(event.player, furyHandler.getMobTypeId());
    }
}