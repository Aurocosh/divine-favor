package aurocosh.divinefavor.common.custom_data.living.capability;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingDataDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ILivingDataHandler.class)
    public static final Capability<ILivingDataHandler> CAPABILITY_LIVING_DATA = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ILivingDataHandler.class, new LivingDataStorage(), DefaultLivingDataHandler.class);
        MinecraftForge.EVENT_BUS.register(new LivingDataDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(ResourceNamer.getFullName("capability_living_data"), new LivingDataProvider());
    }
}