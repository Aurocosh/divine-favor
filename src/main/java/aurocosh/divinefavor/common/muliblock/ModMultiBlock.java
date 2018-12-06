package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class ModMultiBlock extends IForgeRegistryEntry.Impl<ModMultiBlock> {
    public final Vector3i controllerRelative;
    public final List<MultiBlockPart> parts;
    public final Set<Vector3i> positionsSet;
    public final CubeCoordinates boundingBox;

    public ModMultiBlock(String name, Vector3i controllerRelative, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        setRegistryName(name);
        this.controllerRelative = controllerRelative;
        this.boundingBox = boundingBox;
        this.parts = Collections.unmodifiableList(new ArrayList<>(parts));

        int size = 0;
        for (MultiBlockPart part : parts)
            size += part.positions.size();

        int i = 0;
        Vector3i[] positions = new Vector3i[size];
        for (MultiBlockPart part : parts)
            for (Vector3i position : part.positions)
                positions[i++] = position;

        this.positionsSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(positions)));
    }

    public boolean isValid(World world, Vector3i controller){
        Vector3i multiblockOrigin = controller.subtract(controllerRelative);
        for (MultiBlockPart part : parts)
            if (!part.isAllValid(world, multiblockOrigin))
                return false;
        return true;
    }

    public CubeCoordinates getBoundingBoxRelative(){
        return boundingBox.subtract(controllerRelative);
    }
}
