package aurocosh.divinefavor.client.render.entity.minion

import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.entity.layers.LayerStrayClothing
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.entity.monster.EntityStray
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class RenderMinionStray(renderManagerIn: RenderManager) : RenderMinionSkeleton(renderManagerIn) {

    init {
        addLayer<EntityStray, LayerStrayClothing>(LayerStrayClothing(this))
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    override fun getEntityTexture(entity: AbstractSkeleton?): ResourceLocation {
        return STRAY_TEXTURES
    }

    companion object {
        private val STRAY_TEXTURES = ResourceLocation(ConstResources.TEX_STRAY)
    }
}
