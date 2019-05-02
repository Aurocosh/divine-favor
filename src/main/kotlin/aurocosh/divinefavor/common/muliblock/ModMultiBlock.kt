package aurocosh.divinefavor.common.muliblock

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstanceAltar
import aurocosh.divinefavor.common.registry.ModRegistries
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry
import java.util.*

class ModMultiBlock(val name: String, configurations: List<MultiBlockConfiguration>) : IForgeRegistryEntry.Impl<ModMultiBlock>() {
    val configurations: List<MultiBlockConfiguration> = Collections.unmodifiableList(configurations)

    init {
        registryName = ResourceNamer.getFullName("multi_block", name)
        ModRegistries.multiBlocks.register(this)
    }

    fun match(world: World, controller: BlockPos): MultiBlockConfiguration? {
        for (configuration in configurations)
            if (configuration.isValid(world, controller))
                return configuration
        return null
    }

    fun makeMultiBlock(world: World, controller: BlockPos): MultiBlockInstance? {
        val configuration = match(world, controller)
        return if (configuration != null) MultiBlockInstance(this, configuration, controller) else null
    }

    fun makeMultiBlock(spirit: ModSpirit, world: World, controller: BlockPos): MultiBlockInstanceAltar? {
        val configuration = match(world, controller)
        return if (configuration != null) MultiBlockInstanceAltar(this, configuration, controller, spirit) else null
    }
}
