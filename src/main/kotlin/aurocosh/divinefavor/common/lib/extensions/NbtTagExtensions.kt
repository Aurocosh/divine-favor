package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

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
        setter.invoke(this,tag,value)
}

fun NBTTagCompound.hasKey(vararg tags: String): Boolean {
    return tags.all(this::hasKey)
}
