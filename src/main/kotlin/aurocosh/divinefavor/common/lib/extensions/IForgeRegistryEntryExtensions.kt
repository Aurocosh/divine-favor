package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

val <T> IForgeRegistryEntry<T>.regName: ResourceLocation
    get() = this.registryName ?: emptyLocation()

val <T> IForgeRegistryEntry<T>.name: String
    get() = regName.toString()
