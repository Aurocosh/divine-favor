package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TalismanPiercingInferno extends ItemTalisman {
    public static int MAX_PIERCE_SHAPE_SIZE = 25;
    public static int MAX_PIERCE_DEPTH = 10;
    public static int CHANCE_TO_IGNITE = 20;
    private static final int USES = 10;

    public TalismanPiercingInferno() {
        super("piercing_inferno", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        List<BlockPos> piercingShape = UtilCoordinates.getNeighboursWithSameExposedFace(context.pos, context.world, context.facing, MAX_PIERCE_SHAPE_SIZE);
        BlockPos shift = new BlockPos(context.facing.getOpposite().getDirectionVec());
        for (int i = 0; i < MAX_PIERCE_DEPTH; i++) {
            for (BlockPos pos : piercingShape) {
                Block block = UtilRandom.rollDice(CHANCE_TO_IGNITE) ? Blocks.FIRE : Blocks.AIR;
                context.world.setBlockState(pos, block.getDefaultState());
            }
            piercingShape = UtilCoordinates.shiftCoordinates(piercingShape, shift);
        }
    }
}
