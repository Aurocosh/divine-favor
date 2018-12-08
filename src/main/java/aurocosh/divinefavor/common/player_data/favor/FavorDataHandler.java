package aurocosh.divinefavor.common.player_data.favor;

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

public class FavorDataHandler {
    public static IFavorHandler getHandler(Entity entity) {
        if (entity.hasCapability(CAPABILITY_FAVOR, EnumFacing.DOWN))
            return entity.getCapability(CAPABILITY_FAVOR, null);
        return null;
    }

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IFavorHandler.class)
    public static final Capability<IFavorHandler> CAPABILITY_FAVOR = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(IFavorHandler.class, new FavorStorage(), DefaultFavorHandler.class);
        MinecraftForge.EVENT_BUS.register(new FavorDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName(LibCapabilityNames.FAVOR_CAPABILITY), new FavorProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final IFavorHandler original = event.getOriginal().getCapability(CAPABILITY_FAVOR, null);
        final IFavorHandler clone = event.getEntity().getCapability(CAPABILITY_FAVOR, null);
        clone.setAllFavorTypes(original.getAllFavorTypes());
    }
}