package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.AirStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiBlockInstance {
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;
    public final MultiBlockConfiguration configuration;
    public final CubeCoordinates boundingBox;
    public final Set<Vector3i> positionsOfSolids;
    public final Set<Vector3i> positionsOfAir;

    public MultiBlockInstance(ModSpirit spirit, ModMultiBlock multiBlock, MultiBlockConfiguration configuration, Vector3i controllerPosition) {
        this.spirit = spirit;
        this.multiBlock = multiBlock;
        this.configuration = configuration;

        CubeCoordinates bounds = configuration.getBoundingBoxRelative();
        this.boundingBox = bounds.add(controllerPosition);

        Vector3i multiBlockOrigin = controllerPosition.subtract(configuration.controllerRelPosition);

        Set<Vector3i> air = new HashSet<>();
        Set<Vector3i> solids = new HashSet<>();

        for (MultiBlockPart part : configuration.parts)
            if (part.validator instanceof AirStateValidator)
                air.addAll(multiBlockOrigin.add(part.positions));
            else
                solids.addAll(multiBlockOrigin.add(part.positions));

        this.positionsOfAir = Collections.unmodifiableSet(air);
        this.positionsOfSolids = Collections.unmodifiableSet(solids);
    }

    public boolean isSolidPart(Vector3i position){
        return boundingBox.isCoordinateInside(position) && positionsOfSolids.contains(position);
    }

    public boolean isSupposedToBeEmpty(Vector3i position){
        return boundingBox.isCoordinateInside(position) && positionsOfAir.contains(position);
    }
}
