package aurocosh.divinefavor.client.render.entity.minion;

import aurocosh.divinefavor.client.models.ModelMinionZombie;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.minions.EntityMinionHusk;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMinionHusk extends RenderBiped<EntityMinionHusk> {
    private static final ResourceLocation HUSK_TEXTURES = new ResourceLocation(ConstResources.TEX_HUSK_ZOMBIE);

    public RenderMinionHusk(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelMinionZombie(), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelMinionZombie(0.5F, true);
                this.modelArmor = new ModelMinionZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMinionHusk entity) {
        return HUSK_TEXTURES;
    }
}
