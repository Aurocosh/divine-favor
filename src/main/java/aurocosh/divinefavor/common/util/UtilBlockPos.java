package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.constants.BlockPosConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class UtilBlockPos {
    public static Vec3d add(BlockPos blockPos, Vec3d vec3d) {
        return vec3d.add(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
    }

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
        return BlockPosConstants.DIRECT_NEIGHBOURS.get(index);
    }

    public static List<BlockPos> getRandomDirections(int count) {
        count = UtilMath.clamp(count, 1, 6);
        int dirsToRemove = 6 - count;

        List<BlockPos> directions = new LinkedList<>(BlockPosConstants.DIRECT_NEIGHBOURS);
        for (int i = 0; i < dirsToRemove; i++) {
            int index = UtilRandom.nextIntExclusive(0, directions.size());
            directions.remove(index);
        }
        return directions;
    }

    public static List<BlockPos> getNeighbours(BlockPos position) {
        List<BlockPos> neighbours = new LinkedList<>();
        for (BlockPos direction : BlockPosConstants.DIRECT_NEIGHBOURS)
            neighbours.add(position.add(direction));
        return neighbours;
    }

    public static int[] serialize(Collection<BlockPos> posCollection) {
        int[] array = new int[posCollection.size() * 3];
        int i = 0;
        for (BlockPos pos : posCollection) {
            array[i++] = pos.getX();
            array[i++] = pos.getY();
            array[i++] = pos.getZ();
        }

        return array;
    }

    public static ArrayList<BlockPos> deserialize(int[] array) {
        int posCount = array.length / 3;
        ArrayList<BlockPos> posArrayList = new ArrayList<>();

        for (int i = 0, j = 0; i < posCount; i++) {
            int x = array[j++];
            int y = array[j++];
            int z = array[j++];
            posArrayList.add(new BlockPos(x, y, z));
        }
        return posArrayList;
    }
}
