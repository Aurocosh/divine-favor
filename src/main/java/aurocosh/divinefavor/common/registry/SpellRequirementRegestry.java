package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class SpellRequirementRegestry {
    private static ArrayList<SpellRequirement> spellRequirements = new ArrayList<>();

    public static void register(SpellRequirement spellRequirement) {
        spellRequirements.add(spellRequirement);
    }

    @SubscribeEvent
    public void registerObjects(RegistryEvent.Register<SpellRequirement> event) {
        IForgeRegistry registry = event.getRegistry();
        for (SpellRequirement requirement : spellRequirements)
            registry.register(requirement);
    }
}


