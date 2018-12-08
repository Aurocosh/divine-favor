package aurocosh.divinefavor.common.registry.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public final class RegistryGenerator {
    private final static String REGESTRY_PREFIX = "registry";

    @SubscribeEvent
    public static void onCreateRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<ModSpell>()
                .setType(ModSpell.class)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX,"spells"))
                .create();
        new RegistryBuilder<ModFavor>()
                .setType(ModFavor.class)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX,"favors"))
                .create();
        new RegistryBuilder<ModSpirit>()
                .setType(ModSpirit.class)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX,"spirits"))
                .create();
        new RegistryBuilder<ModMultiBlock>()
                .setType(ModMultiBlock.class)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX,"multi_blocks"))
                .create();
    }
}