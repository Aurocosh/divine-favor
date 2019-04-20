package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanMineArrow extends ItemArrowTalisman {
    public ArrowTalismanMineArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        spellArrow.setDespawnDelay(ConfigArrow.mineArrow.despawnDelay);
    }

    @Override
    public boolean onCollideWithPlayer(EntitySpellArrow spellArrow, EntityPlayer entityIn) {
        return false;
    }

    @Override
    public void onUpdate(EntitySpellArrow spellArrow) {
        if (spellArrow.world.isRemote)
            return;
        if (!spellArrow.isInGround())
            return;

        List<EntityLivingBase> livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(spellArrow.getPosition()).grow(ConfigArrow.mineArrow.radius));
        if (livingBases.isEmpty())
            return;

        boolean damageTerrain = ConfigArrow.mineArrow.damageTerrain && !spellArrow.isInWater();
        BlockPos arrowPosition = spellArrow.getPosition();
        spellArrow.world.newExplosion(spellArrow, arrowPosition.getX(), arrowPosition.getY(), arrowPosition.getZ(), ConfigArrow.mineArrow.explosionPower, ConfigArrow.mineArrow.causeFire, damageTerrain);
        spellArrow.setDead();
    }
}
