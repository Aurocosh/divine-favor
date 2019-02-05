package aurocosh.divinefavor.client.render.entity.minion;

import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerStrayClothing;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMinionStray extends RenderMinionSkeleton {
    private static final ResourceLocation STRAY_TEXTURES = new ResourceLocation(ConstResources.TEX_STRAY);

    public RenderMinionStray(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.addLayer(new LayerStrayClothing(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity) {
        return STRAY_TEXTURES;
    }
}
