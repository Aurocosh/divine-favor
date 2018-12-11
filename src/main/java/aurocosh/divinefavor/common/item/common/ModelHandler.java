package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
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

import java.util.Collection;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public final class ModelHandler {
	@SubscribeEvent
	public static void onRegister(ModelRegistryEvent event) {
        Collection<ModItem> items = ModItems.getItems();
        for (ModItem item : items)
            registerItemModel(item);

        Collection<ItemBlock> itemBlocks = ModBlocks.getItemBlocks();
        for (ItemBlock itemBlock : itemBlocks)
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

        final String[] variants = item.getVariants();
        if(variants.length == 1) {
            ModelResourceLocation loc = new ModelResourceLocation(fullName, "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, loc);
            return;
        }

//        final String namespace = fullName.getNamespace();
        for(int i = 0; i < variants.length; i++) {
//			ResourceLocation name = new ResourceLocation(namespace, variants[i]);
			ModelResourceLocation loc = new ModelResourceLocation(fullName + "_" + variants[i], "inventory");
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
