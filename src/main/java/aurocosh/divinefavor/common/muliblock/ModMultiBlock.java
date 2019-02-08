package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstanceAltar;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModMultiBlock extends IForgeRegistryEntry.Impl<ModMultiBlock> {
    public final List<MultiBlockConfiguration> configurations;

    public ModMultiBlock(String name, Vector3i controllerRelative, List<MultiBlockPart> parts, CubeCoordinates boundingBox) {
        List<MultiBlockConfiguration> configurations = new ArrayList<>();
        MultiBlockConfiguration configuration = new MultiBlockConfiguration(controllerRelative, parts, boundingBox);
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);
        configuration = configuration.rotateClockwise();
        configurations.add(configuration);

        this.configurations = Collections.unmodifiableList(configurations);
        setRegistryName(ResourceNamer.getFullName("multi_block", name));
    }
//
//    public boolean isValid(World world, Vector3i controller){
//        Vector3i multiblockOrigin = controller.subtract(controllerRelPosition);
//        for (MultiBlockPart part : parts)
//            if (!part.isAllValid(world, multiblockOrigin))
//                return false;
//        return true;
//    }

    public MultiBlockConfiguration match(World world, Vector3i controller) {
        for (MultiBlockConfiguration configuration : configurations)
            if (configuration.isValid(world, controller))
                return configuration;
        return null;
    }

    public MultiBlockInstance makeMultiBlock(World world, Vector3i controller) {
        MultiBlockConfiguration configuration = match(world, controller);
        return configuration != null ? new MultiBlockInstance(this, configuration, controller) : null;
    }

    public MultiBlockInstanceAltar makeMultiBlock(ModSpirit spirit, World world, Vector3i controller) {
        MultiBlockConfiguration configuration = match(world, controller);
        return configuration != null ? new MultiBlockInstanceAltar(this, configuration, controller, spirit) : null;
    }
}
