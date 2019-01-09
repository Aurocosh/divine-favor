package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class UtilCoordinates {
    @FunctionalInterface
    public interface PositionPredicate {
        boolean isValid(World world, Vector3i pos);
    }

    public static List<BlockPos> getRandomNeighbours(BlockPos start, World world, int neighbourCount, int minNextNode, int maxNextNode, int cycleLimit, PositionPredicate predicate) {
        List<Vector3i> selectedNodes = new ArrayList<>();
        Queue<Vector3i> nodesToVisit = new ArrayDeque<>();
        Set<Vector3i> plannedNodes = new HashSet<>();
        Set<Vector3i> visitedNodes = new HashSet<>();
        nodesToVisit.add(Vector3i.convert(start));

        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && neighbourCount-- > 0) {
            Vector3i nextNode = nodesToVisit.remove();
            plannedNodes.remove(nextNode);
            visitedNodes.add(nextNode);
            selectedNodes.add(nextNode);

            if (!predicate.isValid(world, nextNode))
                continue;

            int neighboursToAdd = UtilRandom.nextInt(minNextNode, maxNextNode);
            List<Vector3i> neighbours = UtilVector3i.getNeighbours(nextNode);
            List<Vector3i> newNodes = new ArrayList<>();
            for (Vector3i neighbour : neighbours)
                if (!visitedNodes.contains(neighbour) && !plannedNodes.contains(neighbour))
                    newNodes.add(neighbour);
            newNodes = UtilGeneric.selectRandom(newNodes, neighboursToAdd);
            nodesToVisit.addAll(newNodes);
            plannedNodes.addAll(newNodes);
        }
        return Vector3i.convert(selectedNodes);
    }

    public static List<BlockPos> getBlocksInSphere(BlockPos center, int radius) {
        List<BlockPos> sphereCoordinates = new ArrayList<>();
        List<BlockPos> cubeCoordinates = getBlocksInCubeRelative(radius);
        int radiusSq = radius * radius;

        for (BlockPos cubeCoordinate : cubeCoordinates) {
            int x = cubeCoordinate.getX();
            int y = cubeCoordinate.getY();
            int z = cubeCoordinate.getZ();
            if (x * x + y * y + z * z <= radiusSq)
                sphereCoordinates.add(cubeCoordinate);
        }
        return shiftCoordinates(sphereCoordinates, center);
    }

    public static List<BlockPos> getBlocksInCube(BlockPos center, int radius) {
        return shiftCoordinates(getBlocksInCubeRelative(radius), center);
    }

    public static List<BlockPos> shiftCoordinates(List<BlockPos> coordinates, BlockPos pos) {
        List<BlockPos> newCoordinates = new ArrayList<>(coordinates.size());
        for (BlockPos coordinate : coordinates)
            newCoordinates.add(coordinate.add(pos.getX(), pos.getY(), pos.getZ()));
        return newCoordinates;
    }

    public static List<BlockPos> getBlocksInCubeRelative(int radius) {
        List<BlockPos> coordinates = new ArrayList<>();
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    coordinates.add(new BlockPos(x, y, z));
        return coordinates;
    }

    public static BlockPos getRandomNeighbour(BlockPos center, int xRadius, int yRadius, int zRadius) {
        int xShift = UtilRandom.nextInt(-xRadius, xRadius);
        int yShift = UtilRandom.nextInt(-yRadius, yRadius);
        int zShift = UtilRandom.nextInt(-zRadius, zRadius);
        return center.add(xShift, yShift, zShift);
    }

    public static BlockPos findPlaceToSpawn(BlockPos start, World world, int limit) {
        BlockPos pos = findPlaceToTeleportAbove(start.down(), world, limit);
        if (pos != null)
            return pos;
        return findPlaceToStandBelow(start.up(), world, limit, true);
    }

    public static BlockPos findPlaceToTeleport(BlockPos start, World world, EnumFacing facing, int limit, boolean needPlaceToStand) {
        BlockPos pos = start;
        while (limit-- > 0) {
            pos = pos.offset(facing);
            if(!world.isAirBlock(pos) || !world.isAirBlock(pos.up()))
                continue;
            if(!needPlaceToStand)
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

    public static List<BlockPos> getNeighboursWithSameExposedFace(BlockPos start, World world, EnumFacing facing, int limit) {
        Vector3i facingVec = new Vector3i(facing.getDirectionVec());

        List<Vector3i> expansionDirs = UtilVector3i.getNeighbourDirections();
        expansionDirs.remove(facingVec);
        expansionDirs.remove(facingVec.inverse());

        List<Vector3i> result = new ArrayList<>();
        Set<Vector3i> explored = new HashSet<>();
        Queue<Vector3i> expansionFront = new ArrayDeque<>();

        Vector3i startVec = Vector3i.convert(start);
        expansionFront.add(startVec);
        explored.add(startVec);

        while (expansionFront.size() > 0 && result.size() < limit) {
            Vector3i nextPos = expansionFront.remove();
            result.add(nextPos);

            for (Vector3i expansionDir : expansionDirs) {
                Vector3i neighbour = nextPos.add(expansionDir);
                if (explored.contains(neighbour))
                    continue;
                explored.add(neighbour);

                BlockPos pos = neighbour.toBlockPos();
                IBlockState state = world.getBlockState(pos);
                if (!state.isSideSolid(world, pos, facing))
                    continue;
                BlockPos posCover = pos.offset(facing);
                if (!world.isAirBlock(posCover))
                    continue;
                expansionFront.add(neighbour);
            }
        }
        return Vector3i.convert(result);
    }
}
