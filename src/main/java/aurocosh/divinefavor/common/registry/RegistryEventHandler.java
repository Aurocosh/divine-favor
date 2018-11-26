package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public final class RegistryEventHandler {
    @SubscribeEvent
    public static void onCreateRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<SpellRequirement>()
            .setType(SpellRequirement.class)
            .setName(new ResourceLocation(LibMisc.MOD_ID, "spell_requirements"))
            .create();
    }
}