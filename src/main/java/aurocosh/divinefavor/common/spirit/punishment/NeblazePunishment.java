package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomEntityList;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NeblazePunishment extends SpiritPunishment {
    public static final int BLOCK_SEARCH_LIMIT = 60;
    private static final DistributedRandomEntityList possibleEnemies = new DistributedRandomEntityList(ConfigPunishments.neblaze.summonedEnemies);

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.setFire(ConfigPunishments.neblaze.ignitionTimeSeconds);
        smeltPartsOfAltar(player, world, instance);
        spawnEnemies(player, world);
        igniteRandomBlocks(world, instance.multiBlockOrigin.toBlockPos());
    }

    private void smeltPartsOfAltar(EntityPlayer player, World world, MultiBlockInstance instance) {
        int blocksToSmelt = ConfigPunishments.neblaze.blocksToMelt.random();
        List<BlockPos> solidsPositions = Vector3i.convert(new ArrayList<>(instance.positionsOfSolids));
        List<BlockPos> netherrackPositions = UtilList.filterList(solidsPositions, pos -> world.getBlockState(pos).getBlock() == Blocks.NETHERRACK);
        List<BlockPos> selectedPositions = UtilRandom.selectRandom(netherrackPositions, blocksToSmelt);
        for (BlockPos position : selectedPositions) {
            if (UtilBlock.canBreakBlock(player, world, position, false)) {
                IBlockState newState = Blocks.FLOWING_LAVA.getDefaultState();
                world.setBlockState(position, newState, 11);
            }
        }
    }

    private void spawnEnemies(EntityPlayer player, World world) {
        int mobsToSummon = ConfigPunishments.neblaze.mobsToSpawn.random();
        int spawnAttempts = mobsToSummon * 10;
        BlockPos playerPosition = player.getPosition();
        UtilAlgoritm.repeatUntilSuccessful(() -> spawnMob(world, playerPosition), mobsToSummon, spawnAttempts);
    }

    private boolean spawnMob(World world, BlockPos pos) {
        int spawnRadius = ConfigPunishments.neblaze.spawnRadius;
        BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(pos, spawnRadius, 0, spawnRadius);
        spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, spawnRadius);
        if (spawnPos != null && possibleEnemies.size() > 0) {
            Class<? extends Entity> clazz = possibleEnemies.getRandom();
            return UtilEntity.spawnEntity(world, spawnPos, clazz);
        }
        return false;
    }

    private void igniteRandomBlocks(World world, BlockPos center) {
        int blocksToIgnite = ConfigPunishments.neblaze.blocksToIgnite.random();
        for (int i = 0; i < blocksToIgnite; i++)
            igniteBlock(world, center);
    }

    private void igniteBlock(World world, BlockPos center) {
        int ignitionRadius = ConfigPunishments.neblaze.ignitionRadius;
        BlockPos ignitionPos = UtilCoordinates.getRandomBlockInRange(world, center, ignitionRadius, BLOCK_SEARCH_LIMIT, pos -> !world.isAirBlock(pos));
        if(ignitionPos == null)
            return;

        List<BlockPos> candidates = Vector3i.convert(UtilVector3i.getNeighbours(new Vector3i(ignitionPos)));
        for (BlockPos pos : candidates)
            if (UtilBlock.ignite(world, pos))
                return;
    }
}
