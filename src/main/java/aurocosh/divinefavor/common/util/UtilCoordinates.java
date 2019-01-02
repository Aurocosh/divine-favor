package aurocosh.divinefavor.common.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class UtilCoordinates {
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
}
