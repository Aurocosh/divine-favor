package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellEarthenDive extends ModSpell {
    private final int MAX_TELEPORTATION_DISTANCE = 64;

    public SpellEarthenDive() {
        super("earthen_dive");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos targetPos = UtilCoordinates.findPlaceToStandBelow(context.pos, context.world, MAX_TELEPORTATION_DISTANCE);
        if (targetPos == null)
            return;
        UtilEntity.teleport(context.player, targetPos);
    }
}
