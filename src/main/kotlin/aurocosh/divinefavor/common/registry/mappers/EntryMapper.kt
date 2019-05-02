package aurocosh.divinefavor.common.registry.mappers

import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry
import java.util.*

class EntryMapper<T> where T : IForgeRegistryEntry<*>, T : IIndexedEntry {
    private val ids: BiMap<Int, T>
    private val names: BiMap<ResourceLocation, T>

    val idMap: Map<Int, T>
        get() = Collections.unmodifiableMap(ids)

    val idMapinversed: Map<T, Int>
        get() = Collections.unmodifiableMap(ids.inverse())

    val nameMap: Map<ResourceLocation, T>
        get() = Collections.unmodifiableMap(names)

    val nameMapinversed: Map<T, ResourceLocation>
        get() = Collections.unmodifiableMap(names.inverse())

    init {
        ids = HashBiMap.create()
        names = HashBiMap.create()
    }

    fun size(): Int {
        return ids.size
    }

    fun register(values: List<T>) {
        for (value in values)
            register(value)
    }

    fun register(value: T) {
        ids[value.id] = value
        names[value.registryName] = value
    }
}
