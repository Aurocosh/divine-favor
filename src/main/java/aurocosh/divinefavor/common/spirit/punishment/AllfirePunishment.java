package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class AllfirePunishment extends SpiritPunishment {
    public static int IGNITION_TIME_SECONDS = 120;

    public static int IGNITION_RADIUS = 40;

    public static int MIN_BLOCKS_TO_IGNITE = 20;
    public static int MAX_BLOCKS_TO_IGNITE = 60;

    public static int BLOCK_SEARCH_LIMIT = 60;

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.setFire(IGNITION_TIME_SECONDS);
        igniteRandomBlocks(world, instance.multiBlockOrigin.toBlockPos());
    }

    private void igniteRandomBlocks(World world, BlockPos center) {
        int blocksToIgnite = UtilRandom.nextInt(MIN_BLOCKS_TO_IGNITE, MAX_BLOCKS_TO_IGNITE);
        for (int i = 0; i < blocksToIgnite; i++)
            igniteBlock(world, center);
    }

    private void igniteBlock(World world, BlockPos center) {
        BlockPos ignitionPos = UtilCoordinates.getRandomNeighbour(center, IGNITION_RADIUS, IGNITION_RADIUS, IGNITION_RADIUS);
        if (world.isAirBlock(ignitionPos)) {
            ignitionPos = UtilCoordinates.findBlock(ignitionPos, EnumFacing.DOWN, BLOCK_SEARCH_LIMIT, pos -> !world.isAirBlock(pos));
            if(ignitionPos == null)
                return;
        }

        List<BlockPos> candidates = Vector3i.convert(UtilVector3i.getNeighbours(new Vector3i(ignitionPos)));
        for (BlockPos pos : candidates)
            if (UtilBlock.ignite(world, pos))
                return;
    }
}
