package aurocosh.divinefavor.common.registry.mappers

import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.*

class EntryIndexMapper<T> where T : IForgeRegistryEntry<*>, T : IIndexedEntry {
    private val values: ArrayList<T> = ArrayList()
    private val names: MutableMap<ResourceLocation, T> = HashMap()

    val nameMap: Map<ResourceLocation, T>
        get() = Collections.unmodifiableMap(names)

    operator fun get(id: Int): T {
        return values[id]
    }

    operator fun get(id: ResourceLocation): T? {
        return names[id]
    }

    fun getValues(): List<T> {
        return Collections.unmodifiableList(values)
    }

    fun register(value: T): Int {
        val id = values.size
        values.add(value)
        names[value.registryName!!] = value
        return id
    }
}
