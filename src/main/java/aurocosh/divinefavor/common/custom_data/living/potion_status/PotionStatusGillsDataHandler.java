package aurocosh.divinefavor.common.custom_data.living.potion_status;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.custom_data.player.escape_plan.IEscapePlanHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionStatusGillsDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IPotionStatusHandler.class)
    public static final Capability<IPotionStatusHandler> CAPABILITY_POTION_STATUS = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IPotionStatusHandler.class, new PotionStatusStorage(), DefaultPotionStatusHandler.class);
        MinecraftForge.EVENT_BUS.register(new PotionStatusGillsDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(ResourceNamer.getFullName("capability_potion_status"), new PotionStatusProvider());
    }
}