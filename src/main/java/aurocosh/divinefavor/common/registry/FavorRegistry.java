package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class FavorRegistry {
    private static ArrayList<ModFavor> list = new ArrayList<>();

    public static void register(ModFavor favor) {
        list.add(favor);
    }

    @SubscribeEvent
    public void registerAll(RegistryEvent.Register<ModSpell> event) {
        IForgeRegistry registry = event.getRegistry();
        for (ModFavor favor : list)
            registry.register(favor);
    }
}


