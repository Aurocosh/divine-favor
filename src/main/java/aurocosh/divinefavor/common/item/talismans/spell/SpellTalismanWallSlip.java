package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SpellTalismanWallSlip extends ItemSpellTalisman {
    private final int MAX_TELEPORTATION_DISTANCE = 64;
    private static final int USES = 10;

    public SpellTalismanWallSlip() {
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
