package aurocosh.divinefavor.common.custom_data.player;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.capability.ILivingDataHandler;
import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFury;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncWindLeash;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class LoginDataSyncer {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;

        IPlayerDataHandler handler = PlayerData.get(event.player);
        new MessageSyncAllSpiritData(handler.getSpiritData()).sendTo(event.player);
        new MessageSyncFury(handler.getFocusedFuryData().getMobTypeId()).sendTo(event.player);
        new MessageSyncGrudge(handler.getGrudgeData().getMobTypeId()).sendTo(event.player);

        ILivingDataHandler livingDataHandler = LivingData.INSTANCE.get(event.player);
        new MessageSyncWindLeash(livingDataHandler.getWindLeashData().getVector()).sendTo(event.player);
    }
}