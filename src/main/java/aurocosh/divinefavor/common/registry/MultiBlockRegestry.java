package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class MultiBlockRegestry {
    private static ArrayList<ModMultiBlock> list = new ArrayList<>();

    public static void register(ModMultiBlock multiBlock) {
        list.add(multiBlock);
    }

    @SubscribeEvent
    public void registerAll(RegistryEvent.Register<ModMultiBlock> event) {
        IForgeRegistry registry = event.getRegistry();
        for (ModMultiBlock spell : list)
            registry.register(spell);
    }
}


