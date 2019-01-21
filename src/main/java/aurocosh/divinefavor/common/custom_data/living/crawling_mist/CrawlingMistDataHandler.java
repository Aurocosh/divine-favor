package aurocosh.divinefavor.common.custom_data.living.crawling_mist;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrawlingMistDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ICrawlingMistHandler.class)
    public static final Capability<ICrawlingMistHandler> CAPABILITY_CRAWLING_MIST = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ICrawlingMistHandler.class, new CrawlingMistStorage(), DefaultCrawlingMistHandler.class);
        MinecraftForge.EVENT_BUS.register(new CrawlingMistDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_crawling_mist"), new CrawlingMistProvider());
    }
}