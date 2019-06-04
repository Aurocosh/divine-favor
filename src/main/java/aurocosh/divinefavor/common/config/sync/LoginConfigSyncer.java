package aurocosh.divinefavor.common.config.sync;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.network.message.client.config.MessageConfigOreBlocks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
public class LoginConfigSyncer {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;
        new MessageConfigOreBlocks().sendTo(event.player);
    }
}