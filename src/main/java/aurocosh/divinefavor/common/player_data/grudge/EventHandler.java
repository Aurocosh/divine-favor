package aurocosh.divinefavor.common.player_data.grudge;

import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP))
            return;

        IGrudgeHandler grudgeHandler = event.player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;
        MessageSyncGrudge.sync(event.player, grudgeHandler.getMobTypeId());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;
        IGrudgeHandler grudgeHandler = player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;

        DamageSource source = event.getSource();
        Entity attacker = source.getTrueSource();
        if (attacker instanceof IMob) {
            grudgeHandler.setGrudge((IMob) attacker);
        }
        else
            grudgeHandler.setMobTypeId(-1);
        MessageSyncGrudge.sync(player, grudgeHandler.getMobTypeId());
    }
}