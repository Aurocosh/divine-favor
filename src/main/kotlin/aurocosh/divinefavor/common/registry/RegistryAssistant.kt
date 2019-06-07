package aurocosh.divinefavor.common.registry

import aurocosh.divinefavor.common.lib.extensions.regName
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry
import java.util.*

class RegistryAssistant<T : IForgeRegistryEntry<*>> {
    private val valueMap: HashMap<ResourceLocation, T> = HashMap()

    val values: Collection<T>
        get() = valueMap.values

    fun getValue(location: ResourceLocation): T? {
        return valueMap[location]
    }

    fun <K : T> getValues(clazz: Class<K>): List<K> {
        val values = ArrayList<K>()
        for (value in valueMap.values)
            if (clazz.isInstance(value))
                values.add(value as K)
        return values
    }

    fun <K : T> register(value: K): K {
        valueMap[value.regName] = value
        CommonRegistry.register(value)
        return value
    }
}
