package aurocosh.divinefavor.common.player_data.spell_count;

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

public class SpellUsesDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ISpellUsesHandler.class)
    public static final Capability<ISpellUsesHandler> CAPABILITY_SPELL_USES = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ISpellUsesHandler.class, new SpellUsesStorage(), DefaultSpellUsesHandler.class);
        MinecraftForge.EVENT_BUS.register(new SpellUsesDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(ResourceNamer.getFullName("capability_spell_uses"), new SpellUsesProvider());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        final ISpellUsesHandler original = event.getOriginal().getCapability(CAPABILITY_SPELL_USES, null);
        final ISpellUsesHandler clone = event.getEntity().getCapability(CAPABILITY_SPELL_USES, null);
        clone.setAllSpellUses(original.getAllSpellUses());
    }
}