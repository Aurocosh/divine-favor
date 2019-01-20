package aurocosh.divinefavor.common.custom_data.player.talisman_uses;

import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncAllTalismanUses;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            ITalismanUsesHandler usesHandler = event.player.getCapability(CAPABILITY_TALISMAN_USES, null);
            assert usesHandler != null;
            new MessageSyncAllTalismanUses(usesHandler).sendTo(event.player);
        }
    }
}