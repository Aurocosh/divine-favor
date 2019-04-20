package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntityClimbingArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanClimbableArrow extends ItemArrowTalisman {
    protected final int despawnDelay;
    protected final float climbingSpeed;
    protected final float climbingDistance;
    protected final float climbingDistanceSq;

    public ArrowTalismanClimbableArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType, float climbingSpeed, float climbingDistance, int despawnDelay) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
        this.despawnDelay = despawnDelay;
        this.climbingSpeed = climbingSpeed;
        this.climbingDistance = climbingDistance;
        this.climbingDistanceSq = climbingDistance * climbingDistance;
    }

    @Override
    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        EntityClimbingArrow arrow = new EntityClimbingArrow(world, shooter);
        arrow.setClimbingStats(climbingSpeed, climbingDistanceSq, despawnDelay);
        return arrow;
    }

    public boolean onCollideWithPlayer(EntitySpellArrow spellArrow, EntityPlayer player) {
        return false;
    }
}
