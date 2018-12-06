package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;

import java.util.*;

public class ModMultiBlockInstance {
    public final ModMultiBlock multiBlock;
    public final CubeCoordinates boundingBox;
    public final Set<Vector3i> positions;

    public ModMultiBlockInstance(ModMultiBlock multiBlock, Vector3i controllerPosition) {
        this.multiBlock = multiBlock;

        CubeCoordinates bounds = multiBlock.getBoundingBoxCentered();
        this.boundingBox = bounds.getCenteredCube(controllerPosition);

        List<Vector3i> positions = Arrays.asList(boundingBox.getAllPositionsInside());
        this.positions = Collections.unmodifiableSet(new HashSet<>(positions));
    }

    public boolean isPartOfMultiblock(Vector3i position){
        return boundingBox.isCoordinateInside(position) && positions.contains(position);
    }
}
