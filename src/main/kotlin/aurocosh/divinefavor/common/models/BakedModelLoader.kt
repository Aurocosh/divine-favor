package aurocosh.divinefavor.common.models

import aurocosh.divinefavor.DivineFavor
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.IResourceManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ICustomModelLoader
import net.minecraftforge.client.model.IModel

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

class BakedModelLoader : ICustomModelLoader {
    override fun accepts(modelLocation: ResourceLocation): Boolean {
        if (modelLocation.namespace != DivineFavor.MOD_ID)
            return false
        return if (modelLocation is ModelResourceLocation && modelLocation.variant == "inventory") false else NAMES.contains(modelLocation.path)
    }

    override fun loadModel(modelLocation: ResourceLocation): IModel {
        return GOO_MODEL
    }

    override fun onResourceManagerReload(resourceManager: IResourceManager) {

    }

    companion object {
        private val GOO_MODEL = EtherealGooModel()
        private val NAMES = setOf("ethereal_goo")
    }
}
