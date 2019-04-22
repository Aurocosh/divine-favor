package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.entries.talismans.arrow.ExplosiveArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanExplosive extends ItemArrowTalisman {
    private final int explosionPower;
    private final boolean damageTerrain;
    private final boolean causeFire;

    public ArrowTalismanExplosive(String name, ModSpirit spirit, Color color, EnumSet<ArrowOptions> options, ArrowType arrowType, ExplosiveArrow config) {
        super(name, spirit, config.favorCost, color, config.damage, options, arrowType);
        explosionPower = config.explosionPower;
        damageTerrain = config.damageTerrain;
        causeFire = config.causeFire;
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        boolean damageTerrain = this.damageTerrain && !spellArrow.isInWater();
        BlockPos arrowPosition = spellArrow.getPosition();
        spellArrow.world.newExplosion(spellArrow, arrowPosition.getX(), arrowPosition.getY(), arrowPosition.getZ(), explosionPower, causeFire, damageTerrain);
        return true;
    }
}
