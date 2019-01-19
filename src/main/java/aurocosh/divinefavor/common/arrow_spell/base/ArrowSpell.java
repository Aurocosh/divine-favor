package aurocosh.divinefavor.common.arrow_spell.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public abstract class ArrowSpell {
    public ArrowSpell() {
    }

    public void cast(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (target.world.isRemote)
            performActionClient(target, shooter, arrow);
        else
            performActionServer(target, shooter, arrow);
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }

    protected void performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }
}