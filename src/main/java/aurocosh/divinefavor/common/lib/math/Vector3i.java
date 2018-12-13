package aurocosh.divinefavor.common.lib.math;

import aurocosh.divinefavor.common.lib.interfaces.IDeepCopy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector3i implements IDeepCopy<Vector3i> {
    public static final Vector3i ZERO = new Vector3i(0, 0, 0);
    public static final Vector3i MIN = new Vector3i(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final Vector3i MAX = new Vector3i(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static final Vector3i WEST = new Vector3i(-1, 0, 0);
    public static final Vector3i EAST = new Vector3i(1, 0, 0);
    public static final Vector3i DOWN = new Vector3i(0, -1, 0);
    public static final Vector3i UP = new Vector3i(0, 1, 0);
    public static final Vector3i NORTH = new Vector3i(0, 0, -1);
    public static final Vector3i SOUTH = new Vector3i(0, 0, 1);

    public final int x;
    public final int y;
    public final int z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(int n) {
        x = n;
        y = n;
        z = n;
    }

    public static Vector3i convert(BlockPos pos) {
        return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
    }

    public static List<BlockPos> convert(List<Vector3i> vectors) {
        List<BlockPos> posList = new ArrayList<>(vectors.size());
        for (Vector3i vector : vectors)
            posList.add(vector.toBlockPos());
        return posList;
    }

    public static List<Vector3i> convertPos(List<BlockPos> posList) {
        List<Vector3i> vectors = new ArrayList<>(posList.size());
        for (BlockPos pos : posList)
            vectors.add(Vector3i.convert(pos));
        return vectors;
    }

    public static BlockPos[] convert(Vector3i[] vectors) {
        BlockPos[] posList = new BlockPos[vectors.length];
        for (int i = 0; i < vectors.length; i++)
            posList[i] = vectors[i].toBlockPos();
        return posList;
    }

    public static Vector3i fromLong(long value) {
        return Vector3i.convert(BlockPos.fromLong(value));
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Vector3i))
            return false;
        if (o == this)
            return true;

        Vector3i other = (Vector3i) o;
        return x == other.x && y == other.y && z == other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (x ^ (x >>> 32));
        result = prime * result + (y ^ (y >>> 32));
        result = prime * result + (z ^ (z >>> 32));
        return result;
    }

    public Vector3i inverse() {
        return new Vector3i(-x, -y, -z);
    }

    public Vector3i add(Vector3i other) {
        return new Vector3i(
                x + other.x,
                y + other.y,
                z + other.z
        );
    }

    public Vector3i subtract(Vector3i vector3i) {
        return new Vector3i(
                x - vector3i.x,
                y - vector3i.y,
                z - vector3i.z
        );
    }

    public Vector3i multiply(int value) {
        return new Vector3i(x * value, y * value, z * value);
    }

    public Vector3i divide(int value) {
        return new Vector3i(x / value, y / value, z / value);
    }

    public int dot(Vector3i other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3i getMinCoordinates(Vector3i vector) {
        int xMin = Math.min(x, vector.x);
        int yMin = Math.min(y, vector.y);
        int zMin = Math.min(z, vector.z);
        return new Vector3i(xMin, yMin, zMin);
    }

    public Vector3i getMaxCoordinates(Vector3i vector) {
        int xMax = Math.max(x, vector.x);
        int yMax = Math.max(y, vector.y);
        int zMax = Math.max(z, vector.z);
        return new Vector3i(xMax, yMax, zMax);
    }

    //    public Vector3i getRealativePositionTo(Vector3i relativeTo){
//        return this.subtract(relativeTo);
//    }
    public Vector3i getRealativePosition(Vector3i position) {
        return position.subtract(this);
    }

    public List<Vector3i> subtract(List<Vector3i> positions) {
        Vector3i[] positionArray = new Vector3i[positions.size()];
        for (int i = 0; i < positions.size(); i++)
            positionArray[i] = positions.get(i).subtract(this);
        return Arrays.asList(positionArray);
    }

    public List<Vector3i> add(List<Vector3i> positions) {
        Vector3i[] positionArray = new Vector3i[positions.size()];
        for (int i = 0; i < positions.size(); i++)
            positionArray[i] = positions.get(i).add(this);
        return Arrays.asList(positionArray);
    }

    public boolean orthogonal(Vector3i other) {
        return dot(other) == 0;
    }

    public Vector3i cross(Vector3i other) {
        int newX = y * other.z - z * other.y;
        int newY = z * other.x - x * other.z;
        int newZ = x * other.y - y * other.x;
        return new Vector3i(newX, newY, newZ);
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquare());
    }

    public boolean isDistanceLessThen(Vector3i other, double distance){
        Vector3i vec = this.subtract(other);
        return vec.magnitudeSquare() < distance * distance;
    }

    public int magnitudeSquare() {
        return x * x + y * y + z * z;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(x, y, z);
    }

    public Vec3i toVec3i() {
        return new Vec3i(x, y, z);
    }

    @Override
    public Vector3i deepCopy() {
        return new Vector3i(x, y, z);
    }

    /**
     * Serialize this BlockPos into a long value
     */
    public long toLong() {
        return toBlockPos().toLong();
    }
}