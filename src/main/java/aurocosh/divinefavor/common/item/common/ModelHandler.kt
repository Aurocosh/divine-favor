package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.base.ICustomMeshHolder
import aurocosh.divinefavor.common.item.base.IModelHolder
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
object ModelHandler {
    @SubscribeEvent
    fun onRegister(event: ModelRegistryEvent) {
        for (item in ModRegistries.items.values)
            registerItemModel(item)

        for (itemBlock in ModRegistries.itemBlocks.values)
            registerBlockItemModel(itemBlock)
    }

    private fun <T> registerItemModel(item: T) where T : Item, T : IModelHolder {
        if (item is ICustomMeshHolder)
            ModelLoader.setCustomMeshDefinition(item, item.customMeshDefinition)
        else
            registerStandardModels(item)
    }

    private fun <T> registerStandardModels(item: T) where T : Item, T : IModelHolder {
        val fullName = item.registryName ?: return
        val modelPath = fullName.namespace + ":" + item.texturePath
        val loc = ModelResourceLocation(modelPath, "inventory")
        ModelLoader.setCustomModelResourceLocation(item, 0, loc)
    }

    private fun registerBlockItemModel(item: ItemBlock) {
        val name = item.registryName ?: return
        val loc = ModelResourceLocation(name, "inventory")
        ModelLoader.setCustomModelResourceLocation(item, 0, loc)
    }
}
