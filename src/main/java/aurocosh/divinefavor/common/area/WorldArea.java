package aurocosh.divinefavor.common.area;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldArea {
    private Set<Vector3i> positions;
    private CubeCoordinates boundingBox;

    public WorldArea() {

        positions = new HashSet<>();
        boundingBox = new CubeCoordinates();
    }

    public void addPositions(List<BlockPos> posList) {
        for (BlockPos blockPos : posList)
            positions.add(new Vector3i(blockPos));
        refreshBoundingBox();
    }

    public void addPosition(BlockPos pos) {
        positions.add(new Vector3i(pos));
        refreshBoundingBox();
    }

    public void removePosition(BlockPos pos) {
        positions.remove(new Vector3i(pos));
        refreshBoundingBox();
    }

    public void clearPositions(){
        positions.clear();
        refreshBoundingBox();
    }

    private void refreshBoundingBox() {
        boundingBox = CubeCoordinates.getBoundingBox(positions);
    }

    public boolean isApartOfArea(Vector3i position) {
        return boundingBox.isCoordinateInside(position) && positions.contains(position);
    }
}
