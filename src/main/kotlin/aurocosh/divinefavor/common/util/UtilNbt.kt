package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.item.spell_talismans.copy.MetaItem
import com.google.common.collect.Multiset
import net.minecraft.item.Item
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList

object UtilNbt {
    fun itemCountToNBT(itemCountMap: Multiset<MetaItem>): NBTTagList {
        val tagList = NBTTagList()

        for (entry in itemCountMap.entrySet()) {
            val item = Item.getIdFromItem(entry.element.item)
            val meta = entry.element.meta
            val count = entry.count
            val compound = NBTTagCompound()
            compound.setInteger("item", item)
            compound.setInteger("meta", meta)
            compound.setInteger("count", count)
            tagList.appendTag(compound)
        }
        return tagList
    }
}