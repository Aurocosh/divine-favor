package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

	private static void registerItemModel(ModItem item) {
        ItemMeshDefinition definition = item.getCustomMeshDefinition();
        if(definition != null)
            ModelLoader.setCustomMeshDefinition(item, definition);
        else
            registerStandardModels(item);
	}

	private static void registerStandardModels(ModItem item) {
        ResourceLocation fullName = item.getRegistryName();
        if(fullName == null)
            return;

        final String namespace = fullName.getNamespace();
        final String texturePath = item.getTexturePath();
        final String path = fullName.getPath();
        final String modelPath = namespace + ":" + texturePath + path;

        final String[] variants = item.getVariants();
        for(int i = 0; i < variants.length; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(modelPath + variants[i], "inventory");
			ModelLoader.setCustomModelResourceLocation(item, i, loc);
		}
	}

    private static void registerBlockItemModel(ItemBlock item) {
        ResourceLocation name = item.getRegistryName();
        if(name == null)
            return;
        ModelResourceLocation loc = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, loc);
    }
}
