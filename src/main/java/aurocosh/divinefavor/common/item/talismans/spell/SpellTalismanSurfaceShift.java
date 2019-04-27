package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanSurfaceShift extends ItemSpellTalisman {
    public SpellTalismanSurfaceShift(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos targetPos = UtilCoordinates.INSTANCE.findPlaceToTeleportAbove(context.pos, context.world, ConfigSpells.surfaceShift.maxDistance);
        if (targetPos != null)
            UtilEntity.teleport(context.player, targetPos);
    }
}
