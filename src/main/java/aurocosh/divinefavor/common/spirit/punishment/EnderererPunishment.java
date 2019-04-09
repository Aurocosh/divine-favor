package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EnderererPunishment extends SpiritPunishment {
    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        int radius = ConfigPunishments.endererer.playerTeleportRadius;
        BlockPos destination = UtilCoordinates.getRandomNeighbourSafe(pos, radius, radius, radius);
        UtilEntity.teleport(player, destination);

        teleportPartsOfAltar(player, world, instance);
    }

    private void teleportPartsOfAltar(EntityPlayer player, World world, MultiBlockInstance instance) {
        int blocksToMove = ConfigPunishments.endererer.blocksToMove.random();
        List<BlockPos> solidsPositions = Vector3i.convert(new ArrayList<>(instance.positionsOfSolids));
        List<BlockPos> coalPositions = UtilList.filterList(solidsPositions, pos -> world.getBlockState(pos).getBlock() == Blocks.COAL_BLOCK);
        List<BlockPos> selectedPositions = UtilRandom.selectRandom(coalPositions, blocksToMove);
        for (BlockPos position : selectedPositions)
            swapBlocks(player, world, position);
    }

    private void swapBlocks(EntityPlayer player, World world, BlockPos position) {
        if (!(world.isAirBlock(position) || UtilBlock.canBreakBlock(player, world, position, false)))
            return;
        int blockTeleportRadius = ConfigPunishments.endererer.blockTeleportRadius;
        BlockPos destination = UtilCoordinates.getRandomNeighbourSafe(position, blockTeleportRadius, blockTeleportRadius, blockTeleportRadius);
        if (!(world.isAirBlock(destination) || UtilBlock.canBreakBlock(player, world, destination, false)))
            return;
        IBlockState positionState = world.getBlockState(position);
        IBlockState destinationState = world.getBlockState(destination);

        world.setBlockState(position, destinationState);
        world.setBlockState(destination, positionState);
    }
}
