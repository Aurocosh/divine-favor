package aurocosh.divinefavor.common.player_data.interaction_handler;

import aurocosh.divinefavor.common.constants.LibCapabilityNames;
import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractionDataHandler {
    public static IInteractionHandler getHandler(Entity entity) {
        if (entity.hasCapability(CAPABILITY_INTERACTION, EnumFacing.DOWN))
            return entity.getCapability(CAPABILITY_INTERACTION, null);
        return null;
    }

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IInteractionHandler.class)
    public static final Capability<IInteractionHandler> CAPABILITY_INTERACTION = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IInteractionHandler.class, new InteractionStorage(), DefaultInteractionHandler.class);
        MinecraftForge.EVENT_BUS.register(new InteractionDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName(LibCapabilityNames.INTERACTION_CAPABILITY), new InteractionProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IInteractionHandler original = event.getOriginal().getCapability(CAPABILITY_INTERACTION, null);
        final IInteractionHandler clone = event.getEntity().getCapability(CAPABILITY_INTERACTION, null);
        clone.setLastClickedPositions(original.getLastClickedPositions());
    }
}