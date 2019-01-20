package aurocosh.divinefavor.common.custom_data.living.limp_leg;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LimpLegDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ILimpLegHandler.class)
    public static final Capability<ILimpLegHandler> CAPABILITY_LIMP_LEG = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ILimpLegHandler.class, new LimpLegStorage(), DefaultLimpLegHandler.class);
        MinecraftForge.EVENT_BUS.register(new LimpLegDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(ResourceNamer.getFullName("capability_limp_leg"), new WindLeashProvider());
    }
}