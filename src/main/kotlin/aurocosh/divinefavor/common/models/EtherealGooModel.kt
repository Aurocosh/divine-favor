package aurocosh.divinefavor.common.models

import aurocosh.divinefavor.DivineFavor
import net.minecraft.client.renderer.block.model.IBakedModel
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.IModel
import net.minecraftforge.client.model.ModelLoaderRegistry
import net.minecraftforge.common.model.IModelState
import net.minecraftforge.common.model.TRSRTransformation
import java.util.function.Function

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

class EtherealGooModel : IModel {
    override fun bake(state: IModelState, format: VertexFormat, bakedTextureGetter: Function<ResourceLocation, TextureAtlasSprite>): IBakedModel {
        val gooModel: IModel
        try {
            gooModel = ModelLoaderRegistry.getModel(MODEL_ETHEREAL_GOO)
        } catch (e: Exception) {
            throw Error("Unable to load construction block model", e)
        }

        return EtherealGooBakedModel(gooModel.bake(state, format, bakedTextureGetter))
    }

    override fun getDependencies(): Collection<ResourceLocation> = listOf(MODEL_ETHEREAL_GOO)
    override fun getDefaultState(): TRSRTransformation = TRSRTransformation.identity()

    companion object {
        private val MODEL_ETHEREAL_GOO = ModelResourceLocation(DivineFavor.MOD_ID + ":blank_ethereal_goo")
    }
}
