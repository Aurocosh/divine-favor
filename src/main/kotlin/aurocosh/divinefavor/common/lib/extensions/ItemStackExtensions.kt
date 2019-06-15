package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.Quadruple
import aurocosh.divinefavor.common.stack_properties.StackProperty
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
            return this.tagCompound as NBTTagCompound

        val nbt = NBTTagCompound()
        this.tagCompound = nbt
        return nbt
    }

fun ItemStack.checkForTag(tag: String): Boolean {
    if (!this.hasTagCompound())
        return false
    val compound = this.tagCompound as NBTTagCompound
    return compound.hasKey(tag)
}

fun ItemStack.hasKey(vararg tags: String): Boolean {
    if (!this.hasTagCompound())
        return false
    val compound = this.tagCompound as NBTTagCompound
    return compound.hasKey(*tags)
}

fun <T> ItemStack.get(one: StackProperty<T>): T {
    return one.getValue(this)
}

fun <T, K> ItemStack.get(one: StackProperty<T>, two: StackProperty<K>): Pair<T, K> {
    return Pair(one.getValue(this), two.getValue(this))
}

fun <A, B, C> ItemStack.get(one: StackProperty<A>, two: StackProperty<B>, three: StackProperty<C>): Triple<A, B, C> {
    return Triple(one.getValue(this), two.getValue(this), three.getValue(this))
}

fun <A, B, C, D> ItemStack.get(one: StackProperty<A>, two: StackProperty<B>, three: StackProperty<C>, four: StackProperty<D>): Quadruple<A, B, C, D> {
    return Quadruple(one.getValue(this), two.getValue(this), three.getValue(this), four.getValue(this))
}
