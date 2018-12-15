package aurocosh.divinefavor.common.player_data.focused_fury;

import aurocosh.divinefavor.common.network.message.client.MessageSyncFury;
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

import static aurocosh.divinefavor.common.player_data.focused_fury.FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY;
import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

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