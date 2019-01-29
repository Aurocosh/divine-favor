package aurocosh.divinefavor.common.custom_data.world.capability;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldDataDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IWorldDataHandler.class)
    public static final Capability<IWorldDataHandler> CAPABILITY_WORLD_DATA_DIVINE = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IWorldDataHandler.class, new WorldDataStorage(), DefaultWorldDataHandler.class);
        MinecraftForge.EVENT_BUS.register(new WorldDataDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(ResourceNamer.getFullName("capability_world_data_divine"), new WorldDataProvider());
    }
}