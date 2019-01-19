package aurocosh.divinefavor.common.item.talismans.base.arrow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public abstract class ArrowSpell {
    private final boolean requiresTarget;

    public ArrowSpell(boolean requiresTarget) {
        this.requiresTarget = requiresTarget;
    }

    public void cast(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if(requiresTarget && target == null)
            return;
        if (arrow.world.isRemote)
            performActionClient(target, shooter, arrow);
        else
            performActionServer(target, shooter, arrow);
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }

    protected void performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
    }
}