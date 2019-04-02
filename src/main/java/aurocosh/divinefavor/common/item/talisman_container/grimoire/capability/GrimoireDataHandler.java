package aurocosh.divinefavor.common.item.talisman_container.grimoire.capability;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class GrimoireDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IGrimoireHandler.class)
    public static final Capability<IGrimoireHandler> CAPABILITY_GRIMOIRE = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IGrimoireHandler.class, new GrimoireStorage(), DefaultGrimoireHandler.class);
        MinecraftForge.EVENT_BUS.register(new GrimoireDataHandler());
    }

//    // Allows the provider to be attached to a target entity.
//    @SubscribeEvent
//    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
//        ItemStack object = event.getObject();
//        assert object != null;
//        if(object.getItem() == ModItems.grimoire)
//            event.addCapability(ResourceNamer.getFullName("grimoire_capability"), new SpellBowProvider());
//    }
}