package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SpellHellisphere extends ModSpell {
    public static int EFFECT_RADIUS = 6;

    @Override
    protected void performActionServer(SpellContext context) {
        World world = context.world;
        IBlockState state = Blocks.LAVA.getDefaultState();
        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(context.pos, 5);
        for (BlockPos pos : posList)
            if (!world.isAirBlock(pos))
                world.setBlockState(pos, state);
    }
}
