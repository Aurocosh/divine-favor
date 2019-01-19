package aurocosh.divinefavor.common.player_data.talisman_uses;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TalismanUsesDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ITalismanUsesHandler.class)
    public static final Capability<ITalismanUsesHandler> CAPABILITY_TALISMAN_USES = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ITalismanUsesHandler.class, new TalismanUsesStorage(), DefaultTalismanUsesHandler.class);
        MinecraftForge.EVENT_BUS.register(new TalismanUsesDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_talisman_uses"), new TalismanUsesProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final ITalismanUsesHandler original = event.getOriginal().getCapability(CAPABILITY_TALISMAN_USES, null);
        final ITalismanUsesHandler clone = event.getEntity().getCapability(CAPABILITY_TALISMAN_USES, null);
        clone.setAllUses(original.getAllUses());
    }
}