package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiBlockConfiguration {
    public final String name;
    public final boolean primary;
    public final Vector3i baseRelPosition;
    public final Vector3i controllerRelPosition;
    public final CubeCoordinates boundingBox;
    public final List<MultiBlockPart> parts;

    public MultiBlockConfiguration(String name, boolean primary, Vector3i baseRelPosition, Vector3i controllerRelPosition, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        this.name = name;
        this.primary = primary;
        this.baseRelPosition = baseRelPosition;
        this.controllerRelPosition = controllerRelPosition;
        this.parts = Collections.unmodifiableList(parts);
        this.boundingBox = boundingBox;
    }

    public boolean isValid(World world, Vector3i controller){
        Vector3i multiBlockOrigin = controller.subtract(controllerRelPosition);
        for (MultiBlockPart part : parts)
            if (!part.isAllValid(world, multiBlockOrigin))
                return false;
        return true;
    }

    public MultiBlockConfiguration rotateClockwise(){
        CubeCoordinates boundingBox = getPartsBoundingBox(parts);
        Vector3i sizeVector = boundingBox.getSizeVector();

        List<MultiBlockPart> partsNew = new ArrayList<>(parts.size());
        for (MultiBlockPart part : parts) {
            List<Vector3i> positionsNew = new ArrayList<>(part.positions.size());
            for (Vector3i position : part.positions)
                positionsNew.add(new Vector3i(position.z, position.y, sizeVector.x - position.x - 1));
            partsNew.add(new MultiBlockPart(part.validator,positionsNew));
        }
        Vector3i controllerRelPositionNew = new Vector3i(controllerRelPosition.z, controllerRelPosition.y, sizeVector.x - controllerRelPosition.x - 1);
        CubeCoordinates boundingBoxNew = getPartsBoundingBox(partsNew);
        return new MultiBlockConfiguration(name, false, baseRelPosition, controllerRelPositionNew,partsNew,boundingBoxNew);
    }

    private CubeCoordinates getPartsBoundingBox(List<MultiBlockPart> parts) {
        CubeCoordinates boundingBox = new CubeCoordinates();
        for (MultiBlockPart part : parts)
            boundingBox = boundingBox.expandBoundingBox(part.positions);
        return boundingBox;
    }

    public CubeCoordinates getBoundingBoxRelative(){
        return boundingBox.subtract(controllerRelPosition);
    }
}
