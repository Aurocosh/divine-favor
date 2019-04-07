package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanSurfaceBlink extends ItemSpellTalisman {
    public SpellTalismanSurfaceBlink(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        BlockPos target = getBlinkTarget(context);
        return canWarp(target, context);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos target = getBlinkTarget(context);
        if (canWarp(target, context))
            UtilEntity.teleport(context.player, target);
    }

    private BlockPos getBlinkTarget(TalismanContext context) {
        if (context.pos == null)
            return null;
        if (context.facing == null)
            return null;
        BlockPos target = context.pos.offset(context.facing);
        if (context.facing == EnumFacing.DOWN)
            return target.down();
        if (context.facing == EnumFacing.UP)
            return target;
        return target.down();
    }

    private boolean canWarp(BlockPos target, TalismanContext context) {
        if (target == null)
            return false;
        if (!context.world.isAirBlock(target))
            return false;
        if (!context.world.isAirBlock(target.up()))
            return false;
        int blinkDistance = ConfigSpells.surfaceBlink.distance;
        return context.player.getDistanceSq(target) < blinkDistance * blinkDistance;
    }
}
