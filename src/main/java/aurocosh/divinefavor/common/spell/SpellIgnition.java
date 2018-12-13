package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class SpellIgnition extends ModSpell {
    public SpellIgnition() {
        super("IGNITION");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        BlockPos pos = context.pos;
        World world = context.world;

        if(UtilBlock.ignite(world, pos))
            return true;

        Vec3i shift = context.facing.getDirectionVec();
        pos = pos.subtract(shift);

        if(UtilBlock.ignite(world, pos))
            return true;
        return false;
    }
}
