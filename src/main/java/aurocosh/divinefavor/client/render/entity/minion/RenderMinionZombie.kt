package aurocosh.divinefavor.client.render.entity.minion

import aurocosh.divinefavor.client.models.ModelMinionZombie
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.entity.minions.EntityMinionZombie
import net.minecraft.client.renderer.entity.RenderBiped
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class RenderMinionZombie(renderManagerIn: RenderManager) : RenderBiped<EntityMinionZombie>(renderManagerIn, ModelMinionZombie(), 0.5f) {

    init {
        val layerBipedArmor = object : LayerBipedArmor(this) {
            override fun initArmor() {
                modelLeggings = ModelMinionZombie(0.5f, true)
                modelArmor = ModelMinionZombie(1.0f, true)
            }
        }
        addLayer<EntityLivingBase, LayerBipedArmor>(layerBipedArmor)
    }

    override fun getEntityTexture(entity: EntityMinionZombie?): ResourceLocation {
        return ZOMBIE_TEXTURES
    }

    companion object {
        private val ZOMBIE_TEXTURES = ResourceLocation(ConstResources.TEX_ZOMBIE)
    }
}
