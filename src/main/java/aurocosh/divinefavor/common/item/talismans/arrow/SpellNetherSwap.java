package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowSpell;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.BlockPos;

public class SpellNetherSwap extends ArrowSpell {
    public SpellNetherSwap() {
        super(true);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        BlockPos targetPos = target.getPosition();
        BlockPos shooterPos = shooter.getPosition();
        UtilEntity.teleport(shooter,targetPos);
        UtilEntity.teleport(target,shooterPos);
    }
}
