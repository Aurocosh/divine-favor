package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class ModMultiBlock extends IForgeRegistryEntry.Impl<ModMultiBlock> {
    public final List<MultiBlockPart> parts;
    public final Set<Vector3i> positionsSet;
    public final CubeCoordinates boundingBox;

    public ModMultiBlock(String name, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        setRegistryName(name);
        this.boundingBox = boundingBox;
        this.parts = Collections.unmodifiableList(new ArrayList<>(parts));

        Vector3i[] positions = new Vector3i[parts.size()];
        for (int i = 0; i < parts.size(); i++)
            positions[i] = parts.get(i).position;

        this.positionsSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(positions)));
    }

    public boolean isValid(World world, BlockPos controllerPos){
        Vector3i controllerPosition = Vector3i.convert(controllerPos);
        for (MultiBlockPart part : parts) {
            BlockPos pos = part.position.add(controllerPosition).toBlockPos();
            Block block = world.getBlockState(pos).getBlock();
            if(block != part.block)
                return false;
        }
        return true;
    }
}
