package aurocosh.divinefavor.common.state_mappers.common;

import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class CustomStateMapperHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerMappers(ModelRegistryEvent event) {
        for (ModBlock block : ModRegistries.blocks.getValues())
            if (block instanceof ICustomStateMappedBlock)
                ModelLoader.setCustomStateMapper(block, ((ICustomStateMappedBlock) block).getCustomStateMapper());
    }
}
