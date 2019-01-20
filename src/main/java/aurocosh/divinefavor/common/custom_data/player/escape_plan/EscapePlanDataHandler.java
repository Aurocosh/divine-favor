package aurocosh.divinefavor.common.custom_data.player.escape_plan;

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

public class EscapePlanDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IEscapePlanHandler.class)
    public static final Capability<IEscapePlanHandler> CAPABILITY_ESCAPE_PLAN = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IEscapePlanHandler.class, new EscapePlanStorage(), DefaultEscapePlanHandler.class);
        MinecraftForge.EVENT_BUS.register(new EscapePlanDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_escape_plan"), new EscapePlanProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IEscapePlanHandler original = event.getOriginal().getCapability(CAPABILITY_ESCAPE_PLAN, null);
        assert original != null;
        final IEscapePlanHandler clone = event.getEntity().getCapability(CAPABILITY_ESCAPE_PLAN, null);
        assert clone != null;
        clone.setGlobalPosition(original.getGlobalPosition());
    }
}