package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.block.soulbound_lectern.NbtProperty
import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.lib.Quadruple
import aurocosh.divinefavor.common.lib.Quintuple
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import java.util.*

fun NBTTagCompound.setBlockPos(tag: String, value: BlockPos) {
    this.setLong(tag, value.toLong())
}

fun NBTTagCompound.getBlockPos(tag: String): BlockPos {
    return BlockPos.fromLong(this.getLong(tag))
}

fun NBTTagCompound.setVec3d(tagPrefix: String, vector: Vec3d) {
    this.setDouble(tagPrefix + "X", vector.x)
    this.setDouble(tagPrefix + "Y", vector.y)
    this.setDouble(tagPrefix + "Z", vector.z)
}

fun NBTTagCompound.getVec3d(tagPrefix: String): Vec3d {
    val x = this.getDouble(tagPrefix + "X")
    val y = this.getDouble(tagPrefix + "Y")
    val z = this.getDouble(tagPrefix + "Z")
    return Vec3d(x, y, z)
}

fun NBTTagCompound.setBlockState(tag: String, state: IBlockState) {
    val stateTag = NBTTagCompound()
    NBTUtil.writeBlockState(stateTag, state)
    this.setTag(tag, stateTag)
}

fun NBTTagCompound.getBlockState(tag: String): IBlockState {
    if (!this.hasKey(tag))
        return Blocks.AIR.defaultState
    return NBTUtil.readBlockState(this.getCompoundTag(tag))
}

fun <T> NBTTagCompound.fallback(tag: String, getter: (NBTTagCompound, String) -> T, fallback: T): T {
    return if (this.hasKey(tag))
        getter.invoke(this, tag)
    else
        fallback
}

fun <T> NBTTagCompound.fallbackNull(tag: String, getter: (NBTTagCompound, String) -> T, fallback: T? = null): T? {
    return if (this.hasKey(tag))
        getter.invoke(this, tag)
    else
        fallback
}

fun <T> NBTTagCompound.setNullable(tag: String, setter: (NBTTagCompound, String, T) -> Unit, value: T?) {
    if (value != null)
        setter.invoke(this, tag, value)
}

private const val tagItem = "item"
private const val tagMeta = "meta"

fun NBTTagCompound.setMetaItem(tag: String, metaItem: MetaItem) {
    val itemTag = NBTTagCompound()
    val itemId = metaItem.item.regName.toString()
    itemTag.setString(tagItem, itemId)
    itemTag.setInteger(tagMeta, metaItem.meta)
    this.setTag(tag, itemTag)
}

fun NBTTagCompound.getMetaItem(tag: String): MetaItem {
    if (!this.hasKey(tag))
        return MetaItem()
    val itemTag = this.getCompoundTag(tag)
    val itemId = itemTag.getString(tagItem)
    val item = Item.getByNameOrId(itemId) ?: return MetaItem()
    val meta = itemTag.getInteger(tagMeta)
    return MetaItem(item, meta)
}

fun NBTTagCompound.hasKey(vararg tags: String): Boolean {
    return tags.all(this::hasKey)
}

private const val defaultKeyTag = "key"
private const val defaultValueTag = "value"

fun <K, V> NBTTagCompound.setMap(tag: String, map: Map<K, V>, keyWriter: (NBTTagCompound, String, K) -> Unit, valueWriter: (NBTTagCompound, String, V) -> Unit, tagKey: String = defaultKeyTag, tagValue: String = defaultValueTag) {
    val tagList = NBTTagList()
    for ((key, value) in map) {
        val pairTag = NBTTagCompound()
        keyWriter.invoke(pairTag, tagKey, key)
        valueWriter.invoke(pairTag, tagValue, value)
        tagList.appendTag(pairTag)
    }
    this.setTag(tag, tagList)
}

fun <K, V> NBTTagCompound.getMap(tag: String, keyReader: (NBTTagCompound, String) -> K, valueReader: (NBTTagCompound, String) -> V, tagKey: String = defaultKeyTag, tagValue: String = defaultValueTag): MutableMap<K, V> {
    val tagList = this.getTag(tag) as? NBTTagList ?: return HashMap()
    val map = HashMap<K, V>()
    for (i in 0 until tagList.tagCount()) {
        val pairTag = tagList.getCompoundTagAt(i)
        val key = keyReader.invoke(pairTag, tagKey)
        val value = valueReader.invoke(pairTag, tagValue)
        map[key] = value
    }
    return map
}


fun <T : Any> NBTTagCompound.set(property: NbtProperty<T>, value: T): Boolean {
    return property.setValue(this, value)
}

fun <T : Any> NBTTagCompound.isPropertySet(property: NbtProperty<T>): Boolean {
    return this.hasKey(property.tag)
}

fun <T : Any> NBTTagCompound.getOrNull(property: NbtProperty<T>): T? {
    if (this.isPropertySet(property))
        return property.getValue(this)
    return null
}

fun <T : Any> NBTTagCompound.get(one: NbtProperty<T>): T {
    return one.getValue(this)
}

fun <T : Any, K : Any> NBTTagCompound.get(one: NbtProperty<T>, two: NbtProperty<K>): Pair<T, K> {
    return Pair(one.getValue(this), two.getValue(this))
}

fun <A : Any, B : Any, C : Any> NBTTagCompound.get(one: NbtProperty<A>, two: NbtProperty<B>, three: NbtProperty<C>): Triple<A, B, C> {
    return Triple(one.getValue(this), two.getValue(this), three.getValue(this))
}

fun <A : Any, B : Any, C : Any, D : Any> NBTTagCompound.get(one: NbtProperty<A>, two: NbtProperty<B>, three: NbtProperty<C>, four: NbtProperty<D>): Quadruple<A, B, C, D> {
    return Quadruple(one.getValue(this), two.getValue(this), three.getValue(this), four.getValue(this))
}

fun <A : Any, B : Any, C : Any, D : Any, E : Any> NBTTagCompound.get(one: NbtProperty<A>, two: NbtProperty<B>, three: NbtProperty<C>, four: NbtProperty<D>, five: NbtProperty<E>): Quintuple<A, B, C, D, E> {
    return Quintuple(one.getValue(this), two.getValue(this), three.getValue(this), four.getValue(this), five.getValue(this))
}