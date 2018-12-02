package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class SpiritRegestry {
    private static ArrayList<ModSpirit> list = new ArrayList<>();

    public static void register(ModSpirit spirit) {
        list.add(spirit);
    }

    @SubscribeEvent
    public void registerAll(RegistryEvent.Register<ModSpirit> event) {
        IForgeRegistry registry = event.getRegistry();
        for (ModSpirit spirit : list)
            registry.register(spirit);
    }
}


