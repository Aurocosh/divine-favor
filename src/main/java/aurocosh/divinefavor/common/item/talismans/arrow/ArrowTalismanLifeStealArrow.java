package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanLifeStealArrow extends ItemArrowTalisman {
    private static final String TAG_STOLE_LIFE = "StoleLife";
    private static final int ENTITY_IGNORE_DELAY = UtilTick.minutesToTicks(1);

    public ArrowTalismanLifeStealArrow(String name, ModSpirit spirit, int favorCost, Color color, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, 0, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        compound.setBoolean(TAG_STOLE_LIFE, false);
    }

    @Override
    protected boolean performActionClient(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target == null)
            return true;
        spellArrow.setEntityIgnoreDelay(ENTITY_IGNORE_DELAY);
        return false;
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target == null)
            return true;
        target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) shooter), ConfigArrow.lifeStealArrow.damage);
        spellArrow.setEntityIgnoreDelay(ENTITY_IGNORE_DELAY);
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        compound.setBoolean(TAG_STOLE_LIFE, true);
        return false;
    }

    @Override
    public boolean onCollideWithPlayer(EntitySpellArrow spellArrow, EntityPlayer player) {
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        boolean stoleLife = compound.getBoolean(TAG_STOLE_LIFE);
        if (stoleLife)
            player.heal(ConfigArrow.lifeStealArrow.heal);
        return super.onCollideWithPlayer(spellArrow, player);
    }
}
