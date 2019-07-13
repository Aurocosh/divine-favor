package aurocosh.divinefavor.common.models

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.doppel.BlockEtherealGoo
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.*
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.common.property.IExtendedBlockState

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

class EtherealGooBakedModel(private val blankConstructionModel: IBakedModel)
    : IBakedModel {

    override fun getQuads(state: IBlockState?, side: EnumFacing?, rand: Long): List<BakedQuad> {
        val extendedBlockState = state as IExtendedBlockState? ?: return emptyList()

        val facadeState = extendedBlockState.getValue(BlockEtherealGoo.FACADE_ID) ?: return blankConstructionModel.getQuads(state, side, rand)
        val extFacadeState = extendedBlockState.getValue(BlockEtherealGoo.FACADE_EXT_STATE)
        val model: IBakedModel

        val layer = MinecraftForgeClient.getRenderLayer()
        if (layer != null && !facadeState.block.canRenderInLayer(facadeState, layer)) // always render in the null layer or the block-breaking textures don't show up
            return emptyList()

        model = getModel(facadeState)
        return try {
            model.getQuads(extFacadeState, side, rand)
        } catch (e: Exception) {
            e.printStackTrace()
            model.getQuads(facadeState, side, rand)
        }
    }

    private fun getModel(state: IBlockState): IBakedModel {
        initTextures()
        return Minecraft.getMinecraft().blockRendererDispatcher.blockModelShapes.getModelForState(state)
    }

    override fun isAmbientOcclusion(): Boolean {
        return true
    }

    /*@Override //This is causing darkness on stairs, and I have no idea why.
    public boolean isAmbientOcclusion(IBlockState state) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        ConstructionID facadeId = extendedBlockState.getValue(ConstructionBlock.FACADEID);
        if (facadeId == null) {
            return true;
        }
        IBlockState facadeState = facadeId.getBlockState();
        return facadeState.getBlock().isOpaqueCube(facadeState);
    }*/

    override fun isGui3d(): Boolean {
        return false
    }

    override fun isBuiltInRenderer(): Boolean {
        return false
    }

    override fun getParticleTexture(): TextureAtlasSprite {
        return blankConstructionModel.particleTexture
    }

    override fun getItemCameraTransforms(): ItemCameraTransforms {
        return ItemCameraTransforms.DEFAULT
    }

    override fun getOverrides(): ItemOverrideList {
        return ItemOverrideList.NONE
    }

    companion object {
        val modelFacade = ModelResourceLocation(DivineFavor.MOD_ID + ":" + "ethereal_goo")

        //    private VertexFormat format;
        private var spriteCable: TextureAtlasSprite? = null

        private fun initTextures() {
            if (spriteCable == null)
                spriteCable = Minecraft.getMinecraft().textureMapBlocks.getAtlasSprite(DivineFavor.MOD_ID + ":blocks/ethereal_goo")
        }
    }
}
