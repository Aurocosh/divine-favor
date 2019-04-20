package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanSniperArrow extends ItemArrowTalisman {
    private final static String TAG_STARTING_POS = "StartingPos";

    public ArrowTalismanSniperArrow(String name, ModSpirit spirit, int favorCost, Color color, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, 0, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        UtilNbt.setVec3d(compound, TAG_STARTING_POS, shooter.getPositionVector());
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        NBTTagCompound compound = spellArrow.getTalismanDataServer();
        Vec3d startingPosition = UtilNbt.getVec3d(compound, TAG_STARTING_POS);

        double distance = startingPosition.distanceTo(spellArrow.getPositionVector());
        spellArrow.setDamage(distance < ConfigArrow.sniperArrow.minDistance ? 0 : ConfigArrow.sniperArrow.damagePerMeter * distance);
        return true;
    }
}
