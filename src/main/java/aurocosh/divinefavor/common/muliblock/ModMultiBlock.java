package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class ModMultiBlock extends IForgeRegistryEntry.Impl<ModMultiBlock> {
    public final List<MultiBlockConfiguration> configurations;

    public ModMultiBlock(String name, Vector3i controllerRelative, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        setRegistryName(name);

        List<MultiBlockConfiguration> configurations = new ArrayList<>();
        MultiBlockConfiguration configuration = new MultiBlockConfiguration(controllerRelative,parts,boundingBox);
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);

        this.configurations = Collections.unmodifiableList(configurations);
    }
//
//    public boolean isValid(World world, Vector3i controller){
//        Vector3i multiblockOrigin = controller.subtract(controllerRelPosition);
//        for (MultiBlockPart part : parts)
//            if (!part.isAllValid(world, multiblockOrigin))
//                return false;
//        return true;
//    }

    public ModMultiBlockInstance makeMultiblock(World world, Vector3i controller){
        for (MultiBlockConfiguration configuration : configurations)
            if (configuration.isValid(world, controller))
                return new ModMultiBlockInstance(this, configuration, controller);
        return null;
    }
}
