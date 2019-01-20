package aurocosh.divinefavor.common.custom_data.player.grudge;

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

public class GrudgeDataHandler {
    public static IGrudgeHandler getHandler(Entity entity) {
        if (entity.hasCapability(CAPABILITY_GRUDGE, EnumFacing.DOWN))
            return entity.getCapability(CAPABILITY_GRUDGE, null);
        return null;
    }

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IGrudgeHandler.class)
    public static final Capability<IGrudgeHandler> CAPABILITY_GRUDGE = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IGrudgeHandler.class, new GrudgeStorage(), DefaultGrudgeHandler.class);
        MinecraftForge.EVENT_BUS.register(new GrudgeDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_grudge"), new GrudgeProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IGrudgeHandler original = event.getOriginal().getCapability(CAPABILITY_GRUDGE, null);
        final IGrudgeHandler clone = event.getEntity().getCapability(CAPABILITY_GRUDGE, null);
        clone.setMobTypeId(original.getMobTypeId());
    }
}