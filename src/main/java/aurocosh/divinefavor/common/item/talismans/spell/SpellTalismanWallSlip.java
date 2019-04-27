package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanWallSlip extends ItemSpellTalisman {
    public SpellTalismanWallSlip(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EnumFacing facing = context.facing;
        if (facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            return;
        BlockPos pos = UtilCoordinates.INSTANCE.findPlaceToTeleport(context.pos.down(), context.world, facing.getOpposite(), ConfigSpells.wallSlip.maxDistance, false);
        if (pos != null)
            UtilEntity.teleport(context.player, pos.down());
    }
}
