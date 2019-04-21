package aurocosh.divinefavor.common.area;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldArea {
    private Set<BlockPos> positions;
    private CubeCoordinates boundingBox;

    public WorldArea() {

        positions = new HashSet<>();
        boundingBox = new CubeCoordinates();
    }

    public void addPositions(List<BlockPos> posList) {
        positions.addAll(posList);
        refreshBoundingBox();
    }

    public void addPosition(BlockPos pos) {
        positions.add(pos);
        refreshBoundingBox();
    }

    public void removePosition(BlockPos pos) {
        positions.remove(pos);
        refreshBoundingBox();
    }

    public void clearPositions() {
        positions.clear();
        refreshBoundingBox();
    }

    private void refreshBoundingBox() {
        boundingBox = CubeCoordinates.getBoundingBox(positions);
    }

    public boolean isApartOfArea(BlockPos position) {
        return boundingBox.isCoordinateInside(position) && positions.contains(position);
    }
}
