package aurocosh.divinefavor.client.render;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrowCurse;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpellArrowCurse extends RenderArrow<EntitySpellArrowCurse> {
    public static final ResourceLocation RES_CURSED_ARROW = new ResourceLocation(ConstResources.RES_CURSED_ARROW);

    public RenderSpellArrowCurse(RenderManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySpellArrowCurse entity) {
        return RES_CURSED_ARROW;
    }
}