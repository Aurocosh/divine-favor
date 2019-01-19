package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TalismanWallSlip extends ItemTalisman {
    private final int MAX_TELEPORTATION_DISTANCE = 64;
    private static final int USES = 10;

    public TalismanWallSlip() {
        super("wall_slip", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EnumFacing facing = context.facing;
        if(facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            return;
        BlockPos pos = UtilCoordinates.findPlaceToTeleport(context.pos.down(), context.world, facing.getOpposite(), MAX_TELEPORTATION_DISTANCE, false);
        if (pos != null)
            UtilEntity.teleport(context.player, pos.down());
    }
}
