package aurocosh.divinefavor.common.receipes.base

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.registries.IForgeRegistryEntry

abstract class ModRecipe(name: String) : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {
    init {
        registryName = ResourceNamer.getFullName(name)
        ModRegistries.recipes.register(this)
    }
}
