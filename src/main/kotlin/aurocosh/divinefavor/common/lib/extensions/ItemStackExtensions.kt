package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

fun ItemStack.isNotEmpty(): Boolean {
    return !this.isEmpty
}

var ItemStack.compound: NBTTagCompound
    set(value) {
        this.tagCompound = value
    }
    get() {
        if (this.hasTagCompound())
            return this.tagCompound!!

        val nbt = NBTTagCompound()
        this.tagCompound = nbt
        return nbt
    }

fun ItemStack.checkForTag(tag: String): Boolean {
    if (!this.hasTagCompound())
        return false
    return this.tagCompound!!.hasKey(tag)
}

fun ItemStack.hasKey(vararg tags: String): Boolean {
    if (!this.hasTagCompound())
        return false
    return this.tagCompound!!.hasKey(*tags)
}