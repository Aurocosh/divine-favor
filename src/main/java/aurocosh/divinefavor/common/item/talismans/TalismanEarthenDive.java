package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

public class TalismanEarthenDive extends ItemTalisman {
    private final int MAX_TELEPORTATION_DISTANCE = 64;
    private static final int USES = 10;

    public TalismanEarthenDive() {
        super("earthen_dive", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos targetPos = UtilCoordinates.findPlaceToStandBelow(context.pos, context.world, MAX_TELEPORTATION_DISTANCE, true);
        if (targetPos == null)
            return;
        UtilEntity.teleport(context.player, targetPos);
    }
}
