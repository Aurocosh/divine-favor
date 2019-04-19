package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanPiercingArrow extends ItemArrowTalisman {
    private static final String TAG_HITS_LEFT = "HitsLeft";
    private static final String TAG_LAST_HIT_ENTITY = "LastHitEntity";

    public ArrowTalismanPiercingArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        NBTTagCompound compound = spellArrow.getTalismanDataCommon();
        compound.setInteger(TAG_HITS_LEFT, ConfigArrow.piercingArrow.maxHits);
        compound.setInteger(TAG_LAST_HIT_ENTITY, 0);
        spellArrow.setTalismanDataCommon(compound);
    }

    @Override
    protected boolean performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target == null)
            return true;
        NBTTagCompound compound = spellArrow.getTalismanDataCommon();
        if (compound.getInteger(TAG_LAST_HIT_ENTITY) == target.getEntityId())
            return false;
        if (compound.getInteger(TAG_HITS_LEFT) == 0)
            return true;
        return false;
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target == null)
            return true;
        NBTTagCompound compound = spellArrow.getTalismanDataCommon();
        if (compound.getInteger(TAG_LAST_HIT_ENTITY) == target.getEntityId())
            return false;
        int hitsLeft = compound.getInteger(TAG_HITS_LEFT);
        if (hitsLeft == 0)
            return true;
        target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) shooter), ConfigArrow.lifeStealArrow.damage);
        compound.setInteger(TAG_HITS_LEFT, hitsLeft - 1);
        compound.setInteger(TAG_LAST_HIT_ENTITY, target.getEntityId());
        spellArrow.setTalismanDataCommon(compound);
        spellArrow.setDamage(spellArrow.getDamage() + ConfigArrow.piercingArrow.damageIncreasePerHit);
        return false;
    }
}
