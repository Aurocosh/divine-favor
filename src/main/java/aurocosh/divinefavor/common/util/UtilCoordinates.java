package aurocosh.divinefavor.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilCoordinates {
    public static List<BlockPos> getRandomNeighbours(BlockPos start, int neighbourCount, final int minNextNode, final int maxNextNode, int cycleLimit, Predicate<BlockPos> predicate) {
        List<BlockPos> selectedNodes = new ArrayList<>();
        Queue<BlockPos> nodesToVisit = new ArrayDeque<>();
        Set<BlockPos> plannedNodes = new HashSet<>();
        Set<BlockPos> visitedNodes = new HashSet<>();
        nodesToVisit.add(start);

        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && neighbourCount-- > 0) {
            BlockPos nextNode = nodesToVisit.remove();
            plannedNodes.remove(nextNode);
            visitedNodes.add(nextNode);
            selectedNodes.add(nextNode);

            if (!predicate.test(nextNode))
                continue;

            int neighboursToAdd = UtilRandom.nextInt(minNextNode, maxNextNode);
            List<BlockPos> neighbours = UtilBlockPos.getNeighbours(nextNode);
            List<BlockPos> newNodes = new ArrayList<>();
            for (BlockPos neighbour : neighbours)
                if (!visitedNodes.contains(neighbour) && !plannedNodes.contains(neighbour))
                    newNodes.add(neighbour);
            newNodes = UtilRandom.selectRandom(newNodes, neighboursToAdd);
            nodesToVisit.addAll(newNodes);
            plannedNodes.addAll(newNodes);
        }
        return selectedNodes;
    }

    public static List<BlockPos> getBlocksInSphere(final BlockPos center, final int radius) {
        final List<BlockPos> cubeCoordinates = getBlocksInRadius(center, radius, radius, radius);
        final int radiusSq = radius * radius;
        return UtilList.select(cubeCoordinates, pos -> center.distanceSq(pos) <= radiusSq);
    }

    public static List<BlockPos> getBlocksInRadius(BlockPos center, int radiusX, int radiusY, int radiusZ) {
        List<BlockPos> coordinates = new ArrayList<>();
        for (int x = -radiusX; x <= radiusX; x++)
            for (int y = -radiusY; y <= radiusY; y++)
                for (int z = -radiusZ; z <= radiusZ; z++)
                    coordinates.add(center.add(x, y, z));
        return coordinates;
    }

    public static BlockPos getRandomNeighbourSafe(BlockPos center, int xRadius, int yRadius, int zRadius) {
        BlockPos destination = UtilCoordinates.getRandomNeighbour(center, xRadius, yRadius, zRadius);
        return new BlockPos(destination.getX(), Math.max(5, destination.getY()), destination.getZ());
    }

    public static BlockPos getRandomNeighbour(BlockPos center, int xRadius, int yRadius, int zRadius) {
        int xShift = UtilRandom.nextInt(-xRadius, xRadius);
        int yShift = UtilRandom.nextInt(-yRadius, yRadius);
        int zShift = UtilRandom.nextInt(-zRadius, zRadius);
        return center.add(xShift, yShift, zShift);
    }

    public static BlockPos getRandomBlockInRange(BlockPos center, int radius, int limit, Predicate<BlockPos> predicate) {
        BlockPos blockPos = getRandomNeighbour(center, radius, radius, radius);
        if (predicate.test(blockPos))
            return blockPos;

        blockPos = findPosition(center, limit, predicate, BlockPos::down);
        return blockPos != null ? blockPos : findPosition(center, limit, predicate, BlockPos::up);
    }

    public static BlockPos findPlaceToStand(BlockPos start, World world, int limit) {
        BlockPos pos = findPlaceToStandBelow(start.up(), world, limit, true);
        if (pos != null)
            return pos;
        return findPlaceToTeleportAbove(start.down(), world, limit);
    }

    public static BlockPos findPlaceToTeleport(BlockPos start, World world, EnumFacing facing, int limit, boolean needPlaceToStand) {
        BlockPos pos = start;
        while (limit-- > 0) {
            pos = pos.offset(facing);
            if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up()))
                continue;
            if (!needPlaceToStand)
                return pos;
            IBlockState state = world.getBlockState(pos.down());
            if (state.getBlock() == Blocks.BEDROCK)
                return null;
            if (state.isSideSolid(world, pos.down(), EnumFacing.UP))
                return pos;
        }
        return null;
    }

    public static BlockPos findPlaceToTeleportAbove(BlockPos start, World world, int limit) {
        BlockPos pos = start;
        boolean previousSecondIsAir = false;
        boolean previousOneIsAir = false;
        while (limit-- > 0) {
            pos = pos.up();
            if (previousOneIsAir && previousSecondIsAir)
                return pos.down();

            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.BEDROCK)
                return null;

            previousSecondIsAir = previousOneIsAir;
            previousOneIsAir = world.isAirBlock(pos);
        }
        return null;
    }

    public static BlockPos findPlaceToStandBelow(BlockPos start, World world, int limit, boolean needPlaceToStand) {
        BlockPos pos = start;
        boolean previousSecondIsAir = false;
        boolean previousOneIsAir = false;
        while (limit-- > 0) {
            pos = pos.down();
            IBlockState state = world.getBlockState(pos);
            if (previousOneIsAir && previousSecondIsAir && (!needPlaceToStand || state.isSideSolid(world, pos, EnumFacing.UP)))
                return pos.up();
            if (state.getBlock() == Blocks.BEDROCK)
                return null;

            previousSecondIsAir = previousOneIsAir;
            previousOneIsAir = world.isAirBlock(pos);
        }
        return null;
    }

    public static BlockPos findPosition(final BlockPos start, int limit, Predicate<BlockPos> predicate, Function<BlockPos, BlockPos> nextPosFunction) {
        BlockPos pos = start;
        while (limit-- > 0) {
            if (predicate.test(pos))
                return pos;
            pos = nextPosFunction.apply(pos);
        }
        return null;
    }

    public static List<BlockPos> floodFill(List<BlockPos> start, List<BlockPos> expansionDirs, Predicate<BlockPos> predicate, int limit) {
        Queue<BlockPos> expansionFront = new ArrayDeque<>(start);
        Set<BlockPos> explored = new HashSet<>(start);
        List<BlockPos> result = new ArrayList<>();

        while (expansionFront.size() > 0 && result.size() < limit) {
            BlockPos nextPos = expansionFront.remove();
            if (!predicate.test(nextPos))
                continue;
            result.add(nextPos);
            for (BlockPos expansionDir : expansionDirs) {
                BlockPos neighbour = nextPos.add(expansionDir);
                if (!explored.contains(neighbour)) {
                    expansionFront.add(neighbour);
                    explored.add(neighbour);
                }
            }
        }
        return result;
    }
}
