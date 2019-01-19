package aurocosh.divinefavor.client.render;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpellArrow extends RenderArrow<EntitySpellArrow> {
    public static final ResourceLocation RES_SPELL_ARROW = new ResourceLocation(ConstResources.RES_SPELL_ARROW);

    public RenderSpellArrow(RenderManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySpellArrow entity) {
        return RES_SPELL_ARROW;
    }
}