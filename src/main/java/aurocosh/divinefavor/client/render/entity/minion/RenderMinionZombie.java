package aurocosh.divinefavor.client.render.entity.minion;

import aurocosh.divinefavor.client.models.ModelMinionZombie;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.entity.minions.MinionZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMinionZombie extends RenderBiped<MinionZombie> {
    private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation(ConstResources.TEX_ZOMBIE);

    public RenderMinionZombie(RenderManager renderManagerIn) {
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
    protected ResourceLocation getEntityTexture(MinionZombie entity) {
        return ZOMBIE_TEXTURES;
    }
}
