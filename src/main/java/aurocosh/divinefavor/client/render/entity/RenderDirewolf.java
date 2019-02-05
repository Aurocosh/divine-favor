package aurocosh.divinefavor.client.render.entity;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderDirewolf extends RenderLiving<EntityDirewolf> {
    private static final ResourceLocation DIREWOLF_TEXTURES = new ResourceLocation(ConstResources.TEX_DIREWOLF);

    public RenderDirewolf(RenderManager manager) {
        super(manager, new ModelWolf(), 0.5F * EntityDirewolf.BODY_SCALE);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityDirewolf livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityDirewolf entity, float partialTickTime)
    {
        GlStateManager.scale(EntityDirewolf.BODY_SCALE, EntityDirewolf.BODY_SCALE, EntityDirewolf.BODY_SCALE);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(@Nonnull EntityDirewolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.isWolfWet()) {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            GlStateManager.color(f, f, f);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDirewolf entity) {
        return DIREWOLF_TEXTURES;
    }
}
