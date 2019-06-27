package aurocosh.divinefavor.common.custom_data.global

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.world.storage.WorldSavedData
import net.minecraftforge.common.util.Constants
import java.util.*

abstract class MapSavedData<K, V>(name: String, private val keyReader: (NBTTagCompound, String) -> K, private val keyWriter: (NBTTagCompound, String, K) -> Unit, private val valueReader: (NBTTagCompound, String) -> V, private val valueWriter: (NBTTagCompound, String, V) -> Unit) : WorldSavedData(name) {
    private val templateMap = HashMap<K, V>()

    operator fun set(key: K, value: V) {
        templateMap[key] = value
        markDirty()
    }

    fun put(key: K, value: V) {
        templateMap[key] = value
        markDirty()
    }

    fun getMap(): Map<K, V> {
        return templateMap
    }

    fun contains(key: K): Boolean {
        return templateMap.contains(key)
    }

    fun get(key: K): V? {
        return templateMap[key]
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        if (!nbt.hasKey(TagMapList))
            return

        val tagList = nbt.getTagList(TagMapList, Constants.NBT.TAG_COMPOUND)
        for (i in 0 until tagList.tagCount()) {
            val pairTag = tagList.getCompoundTagAt(i)
            val key = keyReader.invoke(pairTag, TagKey)
            val value = valueReader.invoke(pairTag, TagValue)
            templateMap[key] = value
        }
    }

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        val tagList = NBTTagList()
        for ((key, value) in templateMap) {
            val pairTag = NBTTagCompound()
            keyWriter.invoke(pairTag, TagKey, key)
            valueWriter.invoke(pairTag, TagValue, value)
            tagList.appendTag(pairTag)
        }
        nbt.setTag(TagMapList, tagList)
        return nbt
    }

    companion object {
        private const val TagKey = "Key"
        private const val TagValue = "Value"
        private const val TagMapList = "MapList"
    }
}
