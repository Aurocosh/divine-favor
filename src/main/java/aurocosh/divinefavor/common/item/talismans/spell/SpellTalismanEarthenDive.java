package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanEarthenDive extends ItemSpellTalisman {
    private final int MAX_TELEPORTATION_DISTANCE = 64;

    public SpellTalismanEarthenDive(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos targetPos = UtilCoordinates.findPlaceToStandBelow(context.pos, context.world, MAX_TELEPORTATION_DISTANCE, true);
        if (targetPos != null)
            UtilEntity.teleport(context.player, targetPos);
    }
}
