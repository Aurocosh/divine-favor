package aurocosh.divinefavor.common.util;

import net.minecraft.util.math.BlockPos;

import java.util.*;

public class UtilBlockPos {
    public static final BlockPos ZERO = new BlockPos(0, 0, 0);
    public static final BlockPos MIN = new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final BlockPos MAX = new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static final BlockPos WEST = new BlockPos(-1, 0, 0);
    public static final BlockPos EAST = new BlockPos(1, 0, 0);
    public static final BlockPos DOWN = new BlockPos(0, -1, 0);
    public static final BlockPos UP = new BlockPos(0, 1, 0);
    public static final BlockPos NORTH = new BlockPos(0, 0, -1);
    public static final BlockPos SOUTH = new BlockPos(0, 0, 1);

    public static final List<BlockPos> HORIZONTAL_ALL = Collections.unmodifiableList(Arrays.asList(
            WEST,
            EAST,
            NORTH,
            SOUTH,
            WEST.add(NORTH),
            EAST.add(NORTH),
            WEST.add(SOUTH),
            EAST.add(SOUTH)
    ));
    public static final List<BlockPos> HORIZONTAL_DIRECT = Collections.unmodifiableList(Arrays.asList(
            WEST,
            EAST,
            NORTH,
            SOUTH
    ));
    public static final List<BlockPos> DIRECT_NEIGHBOURS = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            WEST,
            EAST,
            NORTH,
            SOUTH
    ));

    public static BlockPos inverse(BlockPos blockPos) {
        return new BlockPos(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
    }

    public static BlockPos getMinCoordinates(BlockPos first, BlockPos second) {
        int xMin = Math.min(first.getX(), second.getX());
        int yMin = Math.min(first.getY(), second.getY());
        int zMin = Math.min(first.getZ(), second.getZ());
        return new BlockPos(xMin, yMin, zMin);
    }

    public static BlockPos getMaxCoordinates(BlockPos first, BlockPos second) {
        int xMax = Math.max(first.getX(), second.getX());
        int yMax = Math.max(first.getY(), second.getY());
        int zMax = Math.max(first.getZ(), second.getZ());
        return new BlockPos(xMax, yMax, zMax);
    }

    public static BlockPos getRandomDirection() {
        int index = UtilRandom.nextInt(0, 5);
        return DIRECT_NEIGHBOURS.get(index);
    }

    public static List<BlockPos> getRandomDirections(int count) {
        count = UtilMath.clamp(count, 1, 6);
        int dirsToRemove = 6 - count;

        List<BlockPos> directions = new LinkedList<>(DIRECT_NEIGHBOURS);
        for (int i = 0; i < dirsToRemove; i++) {
            int index = UtilRandom.nextIntExclusive(0, directions.size());
            directions.remove(index);
        }
        return directions;
    }

    public static List<BlockPos> getNeighbours(BlockPos position) {
        List<BlockPos> neighbours = new LinkedList<>();
        for (BlockPos direction : DIRECT_NEIGHBOURS)
            neighbours.add(position.add(direction));
        return neighbours;
    }
}
