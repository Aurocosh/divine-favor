package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.math.MathHelper

fun <T> List<T>.getSafe(index: Int): T {
    return this[clampIndex(index)]
}

fun <T> List<T>.clampIndex(index: Int): Int {
    return MathHelper.clamp(index, 0, this.size - 1)
}

fun <T> List<T>.firstIndex(predicate: (T) -> Boolean): Int {
    for (i in this.indices) {
        val element = this[i]
        if (predicate.invoke(element))
            return i
    }
    return -1
}