package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstanceAltar;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;
import java.util.List;

public class ModMultiBlock extends IForgeRegistryEntry.Impl<ModMultiBlock> {
    public final String name;
    public final List<MultiBlockConfiguration> configurations;

    public ModMultiBlock(String name, List<MultiBlockConfiguration> configurations) {
        this.name = name;
        this.configurations = Collections.unmodifiableList(configurations);
        setRegistryName(ResourceNamer.getFullName("multi_block", name));
        ModRegistries.multiBlocks.register(this);
    }

    public MultiBlockConfiguration match(World world, BlockPos controller) {
        for (MultiBlockConfiguration configuration : configurations)
            if (configuration.isValid(world, controller))
                return configuration;
        return null;
    }

    public MultiBlockInstance makeMultiBlock(World world, BlockPos controller) {
        MultiBlockConfiguration configuration = match(world, controller);
        return configuration != null ? new MultiBlockInstance(this, configuration, controller) : null;
    }

    public MultiBlockInstanceAltar makeMultiBlock(ModSpirit spirit, World world, BlockPos controller) {
        MultiBlockConfiguration configuration = match(world, controller);
        return configuration != null ? new MultiBlockInstanceAltar(this, configuration, controller, spirit) : null;
    }
}
