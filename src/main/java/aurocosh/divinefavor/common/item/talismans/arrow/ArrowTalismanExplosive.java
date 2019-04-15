package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.talismans.arrow.ExplosiveArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanExplosive extends ItemArrowTalisman {
    private final int explosionPower;
    private final boolean damageTerrain;
    private final boolean causeFire;

    public ArrowTalismanExplosive(String name, ModSpirit spirit, int color, EnumSet<ArrowOptions> options, ArrowType arrowType, ExplosiveArrow config) {
        super(name, spirit, config.favorCost, color, config.damage, options, arrowType);
        explosionPower = config.explosionPower;
        damageTerrain = config.damageTerrain;
        causeFire = config.causeFire;
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow, BlockPos blockPos, EnumFacing sideHit) {
        boolean damageTerrain = this.damageTerrain && !arrow.isInWater();
        BlockPos arrowPosition = arrow.getPosition();
        arrow.world.newExplosion(arrow, arrowPosition.getX(), arrowPosition.getY(), arrowPosition.getZ(), explosionPower, causeFire, damageTerrain);
    }
}
