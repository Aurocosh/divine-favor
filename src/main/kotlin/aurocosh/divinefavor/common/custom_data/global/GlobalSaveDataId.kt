package aurocosh.divinefavor.common.custom_data.global

import net.minecraft.world.storage.WorldSavedData

class GlobalSaveDataId<T : WorldSavedData>(val name: String, val clazz: Class<T>, private val factory: (String) -> T) {
    fun makeNewInstance() = factory.invoke(name)
}
