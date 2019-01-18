package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SpellSurfaceBlink extends ModSpell {
    private final double blinkDistance;

    public SpellSurfaceBlink(double blinkDistance) {
        this.blinkDistance = blinkDistance;
    }

    @Override
    public boolean isConsumeCharge(SpellContext context) {
        BlockPos target = getBlinkTarget(context);
        return canWarp(target, context);
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos target = getBlinkTarget(context);
        if (canWarp(target, context))
            UtilEntity.teleport(context.player, target);
    }

    private BlockPos getBlinkTarget(SpellContext context) {
        if(context.pos == null)
            return null;
        if(context.facing == null)
            return null;
        BlockPos target = context.pos.offset(context.facing);
        if(context.facing == EnumFacing.DOWN)
            return target.down();
        if(context.facing == EnumFacing.UP)
            return target;
        return target.down();
    }

    private boolean canWarp(BlockPos target, SpellContext context){
        if(target == null)
            return false;
        if(!context.world.isAirBlock(target))
            return false;
        if(!context.world.isAirBlock(target.up()))
            return false;
        return context.player.getDistanceSq(target) < blinkDistance * blinkDistance;
    }
}
