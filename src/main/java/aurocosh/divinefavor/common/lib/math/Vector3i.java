package aurocosh.divinefavor.common.lib.math;

import net.minecraft.util.math.BlockPos;

public class Vector3i {
    public static final Vector3i ZERO = new Vector3i(0, 0, 0);

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

    public int magnitudeSquare() {
        return x * x + y * y + z * z;
    }

    public static Vector3i fromBlockPos(BlockPos pos) {
        return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockPos toBlockPos(Vector3i vector) {
        return new BlockPos(vector.x,vector.y,vector.z);
    }
}