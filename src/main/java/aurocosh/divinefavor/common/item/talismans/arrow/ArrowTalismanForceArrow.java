package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanForceArrow extends ItemArrowTalisman {
    private final float velocity;

    public ArrowTalismanForceArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, ArrowType arrowType, float velocity) {
        super(name, spirit, favorCost, color, arrowDamage, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), arrowType);
        this.velocity = velocity;
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (!(target instanceof EntityPlayer))
            UtilEntity.addVelocity(target, shooter.getLookVec(), velocity);
        return true;
    }
}
