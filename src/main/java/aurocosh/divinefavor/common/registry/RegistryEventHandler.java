package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public final class RegistryEventHandler {
    @SubscribeEvent
    public static void onCreateRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<ModSpell>()
                .setType(ModSpell.class)
                .setName(new ResourceLocation(LibMisc.MOD_ID, "registry.spells"))
                .create();
        new RegistryBuilder<ModFavor>()
                .setType(ModFavor.class)
                .setName(new ResourceLocation(LibMisc.MOD_ID, "registry.favors"))
                .create();
        new RegistryBuilder<ModSpirit>()
                .setType(ModSpirit.class)
                .setName(new ResourceLocation(LibMisc.MOD_ID, "registry.spirits"))
                .create();
        new RegistryBuilder<ModMultiBlock>()
                .setType(ModMultiBlock.class)
                .setName(new ResourceLocation(LibMisc.MOD_ID, "registry.multi_blocks"))
                .create();
    }
}