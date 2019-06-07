package aurocosh.divinefavor.common.registry.mappers

import aurocosh.divinefavor.common.lib.extensions.regName
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.*

class EntryIndexMapper<T> where T : IForgeRegistryEntry<*>, T : IIndexedEntry {
    val values: ArrayList<T> = ArrayList()

    private val _names: MutableMap<ResourceLocation, T> = HashMap()
    val names: Map<ResourceLocation, T>
        get() = _names

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
        _names[value.regName] = value
        return id
    }
}
