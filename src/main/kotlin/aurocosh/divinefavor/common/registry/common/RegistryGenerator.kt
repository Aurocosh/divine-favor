package aurocosh.divinefavor.common.registry.common

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.RegistryBuilder

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
object RegistryGenerator {
    private val REGESTRY_PREFIX = "registry"

    @SubscribeEvent
    fun onCreateRegistry(event: RegistryEvent.NewRegistry) {
        RegistryBuilder<ModMultiBlock>()
                .setType(ModMultiBlock::class.java)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX, "multi_blocks"))
                .create()
        RegistryBuilder<ModSpirit>()
                .setType(ModSpirit::class.java)
                .setName(ResourceNamer.getFullName(REGESTRY_PREFIX, "spirits"))
                .create()
    }
}