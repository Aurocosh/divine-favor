package aurocosh.divinefavor.common.lib.math;

import aurocosh.divinefavor.common.lib.interfaces.IDeepCopy;

import java.util.Arrays;
import java.util.List;

public class Vector2i implements IDeepCopy<Vector2i> {
    public final int x;
    public final int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(int n) {
        x = n;
        y = n;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Vector2i))
            return false;
        if (o == this)
            return true;

        Vector2i other = (Vector2i) o;
        return x == other.x && y == other.y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (x ^ (x >>> 32));
        result = prime * result + (y ^ (y >>> 32));
        return result;
    }

    public Vector2i inverse() {
        return new Vector2i(-x, -y);
    }

    public Vector2i add(Vector2i other) {
        return new Vector2i(
                x + other.x,
                y + other.y
        );
    }

    public Vector2i subtract(Vector2i vector3i) {
        return new Vector2i(
                x - vector3i.x,
                y - vector3i.y
        );
    }

    public Vector2i multiply(int value) {
        return new Vector2i(x * value, y * value);
    }

    public Vector2i divide(int value) {
        return new Vector2i(x / value, y / value);
    }

    public int dot(Vector2i other) {
        return x * other.x + y * other.y;
    }

    public Vector2i getMinCoordinates(Vector2i vector) {
        int xMin = Math.min(x, vector.x);
        int yMin = Math.min(y, vector.y);
        return new Vector2i(xMin, yMin);
    }

    public Vector2i getMaxCoordinates(Vector2i vector) {
        int xMax = Math.max(x, vector.x);
        int yMax = Math.max(y, vector.y);
        return new Vector2i(xMax, yMax);
    }

    public Vector2i getRealativePosition(Vector2i position) {
        return position.subtract(this);
    }

    public List<Vector2i> subtract(List<Vector2i> positions) {
        Vector2i[] positionArray = new Vector2i[positions.size()];
        for (int i = 0; i < positions.size(); i++)
            positionArray[i] = positions.get(i).subtract(this);
        return Arrays.asList(positionArray);
    }

    public List<Vector2i> add(List<Vector2i> positions) {
        Vector2i[] positionArray = new Vector2i[positions.size()];
        for (int i = 0; i < positions.size(); i++)
            positionArray[i] = positions.get(i).add(this);
        return Arrays.asList(positionArray);
    }

    public boolean orthogonal(Vector2i other) {
        return dot(other) == 0;
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquare());
    }

    public boolean isDistanceLessThen(Vector2i other, double distance){
        Vector2i vec = this.subtract(other);
        return vec.magnitudeSquare() < distance * distance;
    }

    public int magnitudeSquare() {
        return x * x + y * y;
    }

    @Override
    public Vector2i deepCopy() {
        return new Vector2i(x, y);
    }
}