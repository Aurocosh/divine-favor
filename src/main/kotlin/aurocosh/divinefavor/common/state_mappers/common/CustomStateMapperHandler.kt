package aurocosh.divinefavor.common.state_mappers.common

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
object CustomStateMapperHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerMappers(event: ModelRegistryEvent) {
        for (block in ModRegistries.blocks.values)
            if (block is ICustomStateMappedBlock)
                ModelLoader.setCustomStateMapper(block, block.getCustomStateMapper())
    }
}
