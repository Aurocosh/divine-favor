package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.base.IModelHolder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public final class ModelHandler {
	@SubscribeEvent
	public static void onRegister(ModelRegistryEvent event) {
        for (ModItem item : ModRegistries.items.getValues())
            registerItemModel(item);

        for (ItemBlock itemBlock : ModRegistries.itemBlocks.getValues())
            registerBlockItemModel(itemBlock);
	}

	private static <T extends Item & IModelHolder> void registerItemModel(T item) {
        ItemMeshDefinition definition = item.getCustomMeshDefinition();
        if(definition != null)
            ModelLoader.setCustomMeshDefinition(item, definition);
        else
            registerStandardModels(item);
	}

	private static <T extends Item & IModelHolder>  void registerStandardModels(T item) {
        ResourceLocation fullName = item.getRegistryName();
        if(fullName == null)
            return;
        final String modelPath = fullName.getNamespace() + ":" + item.getTexturePath();
        ModelResourceLocation loc = new ModelResourceLocation(modelPath, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, loc);
	}

    private static  void registerBlockItemModel(ItemBlock item) {
        ResourceLocation name = item.getRegistryName();
        if(name == null)
            return;
        ModelResourceLocation loc = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, loc);
    }
}
