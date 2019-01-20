package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class ArrowTalismanNetherSwap extends ItemArrowTalisman {
    public ArrowTalismanNetherSwap() {
        super("nether_swap", 15, Color.green.getRGB(), 0, true, true, ArrowType.SPELL_ARROW);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        BlockPos targetPos = target.getPosition();
        BlockPos shooterPos = shooter.getPosition();
        UtilEntity.teleport(shooter, targetPos);
        UtilEntity.teleport(target, shooterPos);
    }
}
