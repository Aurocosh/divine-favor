package aurocosh.divinefavor.common.registry

import aurocosh.divinefavor.common.core.ResourceNamer.getFullName
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import aurocosh.divinefavor.DivineFavor
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.RegistryEvent.NewRegistry
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.registry.RegistryGenerator2
import net.minecraftforge.registries.RegistryBuilder

// Does not work for some reason if converted to kotlin
@EventBusSubscriber(modid = DivineFavor.MOD_ID)
object RegistryGenerator2 {
    private const val REGESTRY_PREFIX = "registry"
    @SubscribeEvent
    fun onCreateRegistry(event: NewRegistry?) {
        RegistryBuilder<ModMultiBlock>()
                .setType(ModMultiBlock::class.java)
                .setName(getFullName(REGESTRY_PREFIX, "multi_blocks"))
                .create()
        //        new RegistryBuilder<ModSpirit>()
//                .setType(ModSpirit.class)
//                .setName(ResourceNamer.INSTANCE.getFullName(REGESTRY_PREFIX,"spirits"))
//                .create();
    }
}