package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UtilVector3i {
    private static List<Vector3i> directNeighbours = Arrays.asList(
            Vector3i.UP,
            Vector3i.DOWN,
            Vector3i.WEST,
            Vector3i.EAST,
            Vector3i.NORTH,
            Vector3i.SOUTH
    );

    public static List<Vector3i> getNeighbourDirections() {
        return new ArrayList<>(directNeighbours);
    }

    public static Vector3i getRandomDirection() {
        int index = UtilRandom.nextInt(0, 5);
        return directNeighbours.get(index);
    }

    public static List<Vector3i> getRandomDirections(int count) {
        count = UtilMath.clamp(count, 1, 6);
        int dirsToRemove = 6 - count;

        List<Vector3i> directions = new LinkedList<>(directNeighbours);
        for (int i = 0; i < dirsToRemove; i++) {
            int index = UtilRandom.nextIntExclusive(0, directions.size());
            directions.remove(index);
        }
        return directions;
    }

    public static List<Vector3i> getNeighbours(Vector3i position) {
        List<Vector3i> neighbours = new LinkedList<>();
        for (Vector3i direction : directNeighbours)
            neighbours.add(position.add(direction));
        return neighbours;
    }
}
