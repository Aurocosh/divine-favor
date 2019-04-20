package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanFlakArrow extends ItemArrowTalisman {
    private final static float radiusSq = ConfigArrow.flakArrow.radius * ConfigArrow.flakArrow.radius;

    public ArrowTalismanFlakArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        doFlakStuff(spellArrow);
        return true;
    }

    @Override
    public void onUpdate(EntitySpellArrow spellArrow) {
        if (!spellArrow.world.isRemote)
            doFlakStuff(spellArrow);
    }

    private void doFlakStuff(EntitySpellArrow spellArrow) {
        if (spellArrow.isInGround())
            return;

        List<EntityLivingBase> livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(spellArrow.getPosition()).grow(ConfigArrow.flakArrow.radius));
        if (livingBases.isEmpty())
            return;
        EntityLivingBase livingBase = UtilList.findFirst(livingBases, element -> !element.onGround && element.getDistanceSq(spellArrow) <= radiusSq);
        if (livingBase == null)
            return;

        BlockPos arrowPosition = spellArrow.getPosition();
        spellArrow.world.newExplosion(spellArrow, arrowPosition.getX(), arrowPosition.getY(), arrowPosition.getZ(), ConfigArrow.flakArrow.explosionPower, false, false);
        spellArrow.setDead();
    }
}
