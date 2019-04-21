package aurocosh.divinefavor.common.muliblock.instance;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.muliblock.MultiBlockPart;
import aurocosh.divinefavor.common.muliblock.validators.AirStateValidator;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiBlockInstance {
    public final ModMultiBlock multiBlock;
    public final MultiBlockConfiguration configuration;
    public final CubeCoordinates boundingBox;
    public final BlockPos multiBlockOrigin;
    public final Set<BlockPos> positionsOfSolids;
    public final Set<BlockPos> positionsOfAir;

    public MultiBlockInstance(ModMultiBlock multiBlock, MultiBlockConfiguration configuration, BlockPos controllerPosition) {
        this.multiBlock = multiBlock;
        this.configuration = configuration;

        CubeCoordinates bounds = configuration.getBoundingBoxRelative();
        this.boundingBox = bounds.add(controllerPosition);

        multiBlockOrigin = controllerPosition.subtract(configuration.controllerRelPosition);

        Set<BlockPos> air = new HashSet<>();
        Set<BlockPos> solids = new HashSet<>();

        for (MultiBlockPart part : configuration.parts) {
            List<BlockPos> posList = UtilList.process(part.positions, pos -> multiBlockOrigin.add(pos));
            if (part.validator instanceof AirStateValidator)
                air.addAll(posList);
            else
                solids.addAll(posList);
        }

        this.positionsOfAir = Collections.unmodifiableSet(air);
        this.positionsOfSolids = Collections.unmodifiableSet(solids);
    }

    public boolean isSolidPart(BlockPos position) {
        return boundingBox.isCoordinateInside(position) && positionsOfSolids.contains(position);
    }

    public boolean isSupposedToBeEmpty(BlockPos position) {
        return boundingBox.isCoordinateInside(position) && positionsOfAir.contains(position);
    }
}
