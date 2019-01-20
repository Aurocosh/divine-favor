package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

public class SpellTalismanEarthenDive extends ItemSpellTalisman {
    private final int MAX_TELEPORTATION_DISTANCE = 64;
    private static final int USES = 10;

    public SpellTalismanEarthenDive() {
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
