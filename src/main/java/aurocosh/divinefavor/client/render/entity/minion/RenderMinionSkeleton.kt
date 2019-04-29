package aurocosh.divinefavor.client.render.entity.minion

import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.model.ModelSkeleton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderBiped
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor
import net.minecraft.client.renderer.entity.layers.LayerHeldItem
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
open class RenderMinionSkeleton(renderManagerIn: RenderManager) : RenderBiped<AbstractSkeleton>(renderManagerIn, ModelSkeleton(), 0.5f) {

    init {
        addLayer<EntityLivingBase, LayerHeldItem>(LayerHeldItem(this))
        addLayer<EntityLivingBase, LayerBipedArmor>(object : LayerBipedArmor(this) {
            override fun initArmor() {
                modelLeggings = ModelSkeleton(0.5f, true)
                modelArmor = ModelSkeleton(1.0f, true)
            }
        })
    }

    override fun transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f)
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    override fun getEntityTexture(entity: AbstractSkeleton?): ResourceLocation {
        return SKELETON_TEXTURES
    }

    companion object {
        private val SKELETON_TEXTURES = ResourceLocation(ConstResources.TEX_SKELETON)
    }
}