package aurocosh.divinefavor.client.render;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.projectile.EntityCursedArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCursedArrow extends RenderArrow<EntityCursedArrow> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation(ConstResources.RES_ARROW);
    public static final ResourceLocation RES_CURSED_ARROW = new ResourceLocation(ConstResources.RES_CURSED_ARROW);

    public RenderCursedArrow(RenderManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCursedArrow entity) {
        return RES_CURSED_ARROW;
//        return entity.getColor() != 0 ? RES_CURSED_ARROW : RES_ARROW;
    }
}