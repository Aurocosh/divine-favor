package aurocosh.divinefavor.common.player_data.focused_fury;

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

public class FocusedFuryDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IFocusedFuryHandler.class)
    public static final Capability<IFocusedFuryHandler> CAPABILITY_FOCUSED_FURY = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IFocusedFuryHandler.class, new FocusedFuryStorage(), DefaultFocusedFuryHandler.class);
        MinecraftForge.EVENT_BUS.register(new FocusedFuryDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_focused_fury"), new FocusedFuryProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IFocusedFuryHandler original = event.getOriginal().getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert original != null;
        final IFocusedFuryHandler clone = event.getEntity().getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert clone != null;
        clone.setMobTypeId(original.getMobTypeId());
    }
}