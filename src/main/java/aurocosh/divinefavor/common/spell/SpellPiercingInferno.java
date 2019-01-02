package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SpellPiercingInferno extends ModSpell {
    public static int MAX_PIERCE_SHAPE_SIZE = 25;
    public static int MAX_PIERCE_DEPTH = 10;
    public static int CHANCE_TO_IGNITE = 20;

    public SpellPiercingInferno() {
        super("piercing_inferno");
    }

    @Override
    protected void performActionServer(SpellContext context) {
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
