package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

public class SpellSurfaceShift extends ModSpell {
    private final int MAX_TELEPORTATION_DISTANCE = 64;

    public SpellSurfaceShift() {
        super("surface_shift");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos targetPos = UtilCoordinates.findPlaceToTeleportAbove(context.pos, context.world, MAX_TELEPORTATION_DISTANCE);
        if (targetPos == null)
            return;
        UtilEntity.teleport(context.player, targetPos);
    }


}
