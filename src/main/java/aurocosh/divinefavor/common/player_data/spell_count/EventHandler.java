package aurocosh.divinefavor.common.player_data.spell_count;

import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncAllSpellUses;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler.CAPABILITY_SPELL_USES;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            ISpellUsesHandler usesHandler = event.player.getCapability(CAPABILITY_SPELL_USES, null);
            assert usesHandler != null;
            new MessageSyncAllSpellUses(usesHandler).sendTo(event.player);
        }
    }
}