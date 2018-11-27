package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.spell.base.Spell;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class SpellRegestry {
    private static ArrayList<Spell> spellRequirements = new ArrayList<>();

    public static void register(Spell spellRequirement) {
        spellRequirements.add(spellRequirement);
    }

    @SubscribeEvent
    public void registerObjects(RegistryEvent.Register<Spell> event) {
        IForgeRegistry registry = event.getRegistry();
        for (Spell requirement : spellRequirements)
            registry.register(requirement);
    }
}


