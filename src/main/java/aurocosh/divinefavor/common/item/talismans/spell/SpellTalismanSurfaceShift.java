package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

public class SpellTalismanSurfaceShift extends ItemSpellTalisman {
    private static final int MAX_TELEPORTATION_DISTANCE = 64;
    private static final int USES = 10;

    public SpellTalismanSurfaceShift() {
        super("surface_shift", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos targetPos = UtilCoordinates.findPlaceToTeleportAbove(context.pos, context.world, MAX_TELEPORTATION_DISTANCE);
        if (targetPos == null)
            return;
        UtilEntity.teleport(context.player, targetPos);
    }


}
