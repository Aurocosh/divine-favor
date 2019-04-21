package aurocosh.divinefavor.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPing extends ModelBase {
    public ModelRenderer cap6;

    public ModelPing() {
        textureWidth = 64;
        textureHeight = 64; //height is 64 here because block's texture must be 64x64
        cap6 = new ModelRenderer(this, 9, 10);
        cap6.setRotationPoint(0, 0, 0);
        cap6.addBox(-1, -1, -1, 2, 2, 2, 0);
        setRotateAngle(cap6, 0, 0, 0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        cap6.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}