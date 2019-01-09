package aurocosh.divinefavor.common.player_data.pearl_crumbs;

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

public class PearlCrumbsDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IPearlCrumbsHandler.class)
    public static final Capability<IPearlCrumbsHandler> CAPABILITY_PEARL_CRUMBS = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IPearlCrumbsHandler.class, new PearlCrumbsStorage(), DefaultPearlCrumbsHandler.class);
        MinecraftForge.EVENT_BUS.register(new PearlCrumbsDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_pearl_crumbs"), new PearlCrumbsProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IPearlCrumbsHandler original = event.getOriginal().getCapability(CAPABILITY_PEARL_CRUMBS, null);
        assert original != null;
        final IPearlCrumbsHandler clone = event.getEntity().getCapability(CAPABILITY_PEARL_CRUMBS, null);
        assert clone != null;
        clone.setAllPositions(original.getAllPositions());
    }
}