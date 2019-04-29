package aurocosh.divinefavor.client.render.entity

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf
import net.minecraft.client.model.ModelWolf
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderDirewolf(manager: RenderManager) : RenderLiving<EntityDirewolf>(manager, ModelWolf(), 0.5f * EntityDirewolf.BODY_SCALE) {

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    override fun handleRotationFloat(livingBase: EntityDirewolf, partialTicks: Float): Float {
        return livingBase.tailRotation
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    override fun preRenderCallback(entity: EntityDirewolf?, partialTickTime: Float) {
        GlStateManager.scale(EntityDirewolf.BODY_SCALE, EntityDirewolf.BODY_SCALE, EntityDirewolf.BODY_SCALE)
    }

    /**
     * Renders the desired `T` type Entity.
     */
    override fun doRender(entity: EntityDirewolf, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float) {
        if (entity.isWolfWet) {
            val f = entity.brightness * entity.getShadingWhileWet(partialTicks)
            GlStateManager.color(f, f, f)
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks)
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    override fun getEntityTexture(entity: EntityDirewolf): ResourceLocation? {
        return DIREWOLF_TEXTURES
    }

    companion object {
        private val DIREWOLF_TEXTURES = ResourceLocation(ConstResources.TEX_DIREWOLF)
    }
}
