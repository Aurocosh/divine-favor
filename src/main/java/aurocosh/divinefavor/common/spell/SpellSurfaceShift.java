package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellSurfaceShift extends ModSpell {
    private final int MAX_TELEPORTATION_DISTANCE = 64;

    public SpellSurfaceShift() {
        super("surface_shift");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos targetPos = findPlaceToTeleport(context.pos, context.world, MAX_TELEPORTATION_DISTANCE);
        if (targetPos == null)
            return;
        UtilEntity.teleport(context.player, targetPos);
    }

    private BlockPos findPlaceToTeleport(BlockPos start, World world, int limit) {
        BlockPos currentPos = start;
        boolean previousSecondIsAir = false;
        boolean previousOneIsAir = false;
        while (limit-- > 0) {
            currentPos = currentPos.up();
            if (previousOneIsAir && previousSecondIsAir)
                return currentPos.down();

            IBlockState state = world.getBlockState(currentPos);
            if(state.getBlock() == Blocks.BEDROCK)
                return null;

            previousSecondIsAir = previousOneIsAir;
            previousOneIsAir = world.isAirBlock(currentPos);
        }
        return null;
    }
}
