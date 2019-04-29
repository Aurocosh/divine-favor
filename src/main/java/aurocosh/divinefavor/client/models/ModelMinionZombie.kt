package aurocosh.divinefavor.client.models

import net.minecraft.client.model.ModelBiped
import net.minecraft.entity.Entity
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ModelMinionZombie @JvmOverloads constructor(modelSize: Float = 0.0f, p_i1168_2_: Boolean = false) : ModelBiped(modelSize, 0.0f, 64, if (p_i1168_2_) 32 else 64) {

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    override fun setRotationAngles(limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scaleFactor: Float, entityIn: Entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn)
        val flag = entityIn is EntityZombie && entityIn.isArmsRaised
        val f = MathHelper.sin(this.swingProgress * Math.PI.toFloat())
        val f1 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * Math.PI.toFloat())
        this.bipedRightArm.rotateAngleZ = 0.0f
        this.bipedLeftArm.rotateAngleZ = 0.0f
        this.bipedRightArm.rotateAngleY = -(0.1f - f * 0.6f)
        this.bipedLeftArm.rotateAngleY = 0.1f - f * 0.6f
        val f2 = -Math.PI.toFloat() / if (flag) 1.5f else 2.25f
        this.bipedRightArm.rotateAngleX = f2
        this.bipedLeftArm.rotateAngleX = f2
        this.bipedRightArm.rotateAngleX += f * 1.2f - f1 * 0.4f
        this.bipedLeftArm.rotateAngleX += f * 1.2f - f1 * 0.4f
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f
    }
}