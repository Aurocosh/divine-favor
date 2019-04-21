package aurocosh.divinefavor.common.lib.math;

import aurocosh.divinefavor.common.lib.interfaces.IDeepCopy;
import aurocosh.divinefavor.common.util.UtilBlockPos;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.List;

public class CubeCoordinates implements IDeepCopy<CubeCoordinates> {
    public final BlockPos lowerCorner;
    public final BlockPos upperCorner;

    public CubeCoordinates() {
        lowerCorner = BlockPos.ORIGIN;
        upperCorner = BlockPos.ORIGIN;
    }

    public CubeCoordinates(BlockPos firestCorner, BlockPos secondCorner) {
        int lowerX = Math.min(firestCorner.getX(), secondCorner.getX());
        int lowerY = Math.min(firestCorner.getY(), secondCorner.getY());
        int lowerZ = Math.min(firestCorner.getZ(), secondCorner.getZ());

        int upperX = Math.max(firestCorner.getX(), secondCorner.getX());
        int upperY = Math.max(firestCorner.getY(), secondCorner.getY());
        int upperZ = Math.max(firestCorner.getZ(), secondCorner.getZ());

        this.lowerCorner = new BlockPos(lowerX, lowerY, lowerZ);
        this.upperCorner = new BlockPos(upperX, upperY, upperZ);
    }

    public CubeCoordinates add(BlockPos vector) {
        BlockPos lower = lowerCorner.add(vector);
        BlockPos upper = upperCorner.add(vector);
        return new CubeCoordinates(lower, upper);
    }

    public CubeCoordinates subtract(BlockPos vector) {
        return add(BlockPos.ORIGIN.subtract(vector));
    }

    public BlockPos getSizeVector() {
        return new BlockPos(getSizeX(), getSizeY(), getSizeZ());
    }

    public int getSizeX() {
        return upperCorner.getX() - lowerCorner.getX() + 1;
    }

    public int getSizeY() {
        return upperCorner.getY() - lowerCorner.getY() + 1;
    }

    public int getSizeZ() {
        return upperCorner.getZ() - lowerCorner.getZ() + 1;
    }

    public BlockPos[] getAllPositionsInside() {
        int xSize = getSizeX();
        int ySize = getSizeY();
        int zSize = getSizeZ();

        int i = 0;
        int positionCount = xSize * ySize * zSize;
        BlockPos[] positions = new BlockPos[positionCount];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    BlockPos shift = new BlockPos(x, y, z);
                    positions[i++] = lowerCorner.add(shift);
                }
            }
        }
        return positions;
    }

    @Override
    public CubeCoordinates deepCopy() {
        return new CubeCoordinates(lowerCorner, upperCorner);
    }

    public boolean isCoordinateInside(BlockPos coordinate) {
        return lowerCorner.getX() <= coordinate.getX() && coordinate.getX() <= upperCorner.getX()
                && lowerCorner.getY() <= coordinate.getY() && coordinate.getY() <= upperCorner.getY()
                && lowerCorner.getZ() <= coordinate.getZ() && coordinate.getZ() <= upperCorner.getZ();
    }

    public CubeCoordinates expandBoundingBox(List<BlockPos> positions) {
        return getBoundingBox(lowerCorner, upperCorner, positions);
    }

    public static CubeCoordinates getBoundingBox(Collection<BlockPos> positions) {
        BlockPos min = UtilBlockPos.MAX;
        BlockPos max = UtilBlockPos.MIN;
        return getBoundingBox(min, max, positions);
    }

    public static CubeCoordinates getBoundingBox(BlockPos min, BlockPos max, Collection<BlockPos> positions) {
        if (positions.size() == 0)
            return new CubeCoordinates();
        for (BlockPos pos : positions) {
            min = UtilBlockPos.getMinCoordinates(min, pos);
            max = UtilBlockPos.getMaxCoordinates(max, pos);
        }
        return new CubeCoordinates(min, max);
    }
}
