package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiBlockConfiguration {
    public final String name;
    public final boolean primary;
    public final BlockPos baseRelPosition;
    public final BlockPos controllerRelPosition;
    public final CubeCoordinates boundingBox;
    public final List<MultiBlockPart> parts;

    public MultiBlockConfiguration(String name, boolean primary, BlockPos baseRelPosition, BlockPos controllerRelPosition, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        this.name = name;
        this.primary = primary;
        this.baseRelPosition = baseRelPosition;
        this.controllerRelPosition = controllerRelPosition;
        this.parts = Collections.unmodifiableList(parts);
        this.boundingBox = boundingBox;
    }

    public boolean isValid(World world, BlockPos controller) {
        BlockPos multiBlockOrigin = controller.subtract(controllerRelPosition);
        for (MultiBlockPart part : parts)
            if (!part.isAllValid(world, multiBlockOrigin))
                return false;
        return true;
    }

    public MultiBlockConfiguration rotateClockwise() {
        CubeCoordinates boundingBox = getPartsBoundingBox(parts);
        BlockPos sizeVector = boundingBox.getSizeVector();

        List<MultiBlockPart> partsNew = new ArrayList<>(parts.size());
        for (MultiBlockPart part : parts) {
            List<BlockPos> positionsNew = new ArrayList<>(part.positions.size());
            for (BlockPos position : part.positions)
                positionsNew.add(new BlockPos(position.getZ(), position.getY(), sizeVector.getX() - position.getX() - 1));
            partsNew.add(new MultiBlockPart(part.validator, positionsNew));
        }
        BlockPos controllerRelPositionNew = new BlockPos(controllerRelPosition.getZ(), controllerRelPosition.getY(), sizeVector.getX() - controllerRelPosition.getX() - 1);
        CubeCoordinates boundingBoxNew = getPartsBoundingBox(partsNew);
        return new MultiBlockConfiguration(name, false, baseRelPosition, controllerRelPositionNew, partsNew, boundingBoxNew);
    }

    private CubeCoordinates getPartsBoundingBox(List<MultiBlockPart> parts) {
        CubeCoordinates boundingBox = new CubeCoordinates();
        for (MultiBlockPart part : parts)
            boundingBox = boundingBox.expandBoundingBox(part.positions);
        return boundingBox;
    }

    public CubeCoordinates getBoundingBoxRelative() {
        return boundingBox.subtract(controllerRelPosition);
    }
}
