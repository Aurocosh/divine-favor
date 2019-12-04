package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.Quadruple
import aurocosh.divinefavor.common.lib.Quintuple
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList

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

fun <T : Any> ItemStack.set(property: StackProperty<T>, value: T, sync: Boolean = false): Boolean {
    return property.setValue(this, value, sync)
}

fun <T : Any> ItemStack.isPropertySet(property: StackProperty<T>): Boolean {
    return this.checkForTag(property.tag)
}

fun <T : Any> ItemStack.getOrNull(property: StackProperty<T>): T? {
    if(this.isPropertySet(property))
        return property.getValue(this)
    return null
}

fun <T : Any> ItemStack.get(one: StackProperty<T>): T {
    return one.getValue(this)
}

fun <T : Any, K : Any> ItemStack.get(one: StackProperty<T>, two: StackProperty<K>): Pair<T, K> {
    return Pair(one.getValue(this), two.getValue(this))
}

fun <A : Any, B : Any, C : Any> ItemStack.get(one: StackProperty<A>, two: StackProperty<B>, three: StackProperty<C>): Triple<A, B, C> {
    return Triple(one.getValue(this), two.getValue(this), three.getValue(this))
}

fun <A : Any, B : Any, C : Any, D : Any> ItemStack.get(one: StackProperty<A>, two: StackProperty<B>, three: StackProperty<C>, four: StackProperty<D>): Quadruple<A, B, C, D> {
    return Quadruple(one.getValue(this), two.getValue(this), three.getValue(this), four.getValue(this))
}

fun <A : Any, B : Any, C : Any, D : Any, E : Any> ItemStack.get(one: StackProperty<A>, two: StackProperty<B>, three: StackProperty<C>, four: StackProperty<D>, five: StackProperty<E>): Quintuple<A, B, C, D, E> {
    return Quintuple(one.getValue(this), two.getValue(this), three.getValue(this), four.getValue(this), five.getValue(this))
}


fun ItemStack.removeEnchantment(enchantment: Enchantment) {
    if (!this.hasTagCompound())
        return

    val compound = this.tagCompound as NBTTagCompound
    if(!compound.hasKey("ench",9))
        return

    val nbtTagList = compound.getTagList("ench", 10)
    val enchantmentIndex = findEnchantmentIndex(nbtTagList, enchantment)
    nbtTagList.removeTag(enchantmentIndex)

    if (nbtTagList.tagCount() <= 0)
        compound.removeTag("ench")
}

fun findEnchantmentIndex(nbtTagList: NBTTagList, ench: Enchantment): Int {
    for (i in 0 until nbtTagList.tagCount()) {
        val nbtTagCompound = nbtTagList.getCompoundTagAt(i)
        val enchantment = Enchantment.getEnchantmentByID(nbtTagCompound.getShort("id").toInt())
        if (enchantment === ench) {
            return i
        }
    }
    return -1
}
