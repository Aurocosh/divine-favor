package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SpellWallSlip extends ModSpell {
    private final int MAX_TELEPORTATION_DISTANCE = 64;

    @Override
    protected void performActionServer(SpellContext context) {
        EnumFacing facing = context.facing;
        if(facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            return;
        BlockPos pos = UtilCoordinates.findPlaceToTeleport(context.pos.down(), context.world, facing.getOpposite(), MAX_TELEPORTATION_DISTANCE, false);
        if (pos != null)
            UtilEntity.teleport(context.player, pos.down());
    }
}
