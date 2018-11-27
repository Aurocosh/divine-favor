package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.base.Spell;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public final class RegistryEventHandler {
    @SubscribeEvent
    public static void onCreateRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<Spell>()
            .setType(Spell.class)
            .setName(new ResourceLocation(LibMisc.MOD_ID, "registry.spells"))
            .create();
    }
}