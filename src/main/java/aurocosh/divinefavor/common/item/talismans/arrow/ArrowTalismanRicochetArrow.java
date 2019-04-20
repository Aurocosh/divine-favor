package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanRicochetArrow extends ItemArrowTalisman {
    private static final String TAG_BOUNCES_LEFT = "BouncesLeft";

    public ArrowTalismanRicochetArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        compound.setInteger(TAG_BOUNCES_LEFT, ConfigArrow.ricochetArrow.maxBounces);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target != null)
            return true;
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        int bouncesLeft = compound.getInteger(TAG_BOUNCES_LEFT);
        if (bouncesLeft == 0)
            return true;
        compound.setInteger(TAG_BOUNCES_LEFT, bouncesLeft - 1);

        Vec3i sideVectorI = sideHit.getDirectionVec();
        Vec3d motionVector = UtilEntity.getMotionVector(spellArrow);
        float speed = (float) motionVector.length();
        if (speed < ConfigArrow.ricochetArrow.minBounceSpeed)
            return true;

        Vec3d randomChange = UtilRandom.nextDirection().scale(ConfigArrow.ricochetArrow.bounceRandomness);
        Vec3d direction = motionVector.normalize().add(sideVectorI.getX(), sideVectorI.getY(), sideVectorI.getZ()).add(randomChange);
        UtilEntity.setVelocity(spellArrow, direction, speed * ConfigArrow.ricochetArrow.bounceSpeedDecrease);
        return false;
    }
}
