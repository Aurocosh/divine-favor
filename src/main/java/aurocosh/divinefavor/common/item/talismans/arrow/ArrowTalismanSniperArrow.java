package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySniperArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ArrowTalismanSniperArrow extends ItemArrowTalisman {
    public ArrowTalismanSniperArrow(String name, ModSpirit spirit, int favorCost, int color, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, 0, options, arrowType);
    }

    @Override
    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        EntitySniperArrow sniperArrow = new EntitySniperArrow(world, shooter);
        sniperArrow.setStartingPosition(shooter.getPositionVector());
        return sniperArrow;
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow, BlockPos blockPos, EnumFacing sideHit) {
        EntitySniperArrow sniperArrow = (EntitySniperArrow) arrow;
        Vec3d startingPosition = sniperArrow.getStartingPosition();

        double distance = startingPosition.distanceTo(arrow.getPositionVector());
        arrow.setDamage(distance < ConfigArrow.sniperArrow.minDistance ? 0 : ConfigArrow.sniperArrow.damagePerMeter * distance);
    }
}
