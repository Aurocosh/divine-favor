package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.math.MathHelper

fun <T> Array<T>.getSafe(index: Int): T {
    return this[clampIndex(index)]
}

fun <T> Array<T>.clampIndex(index: Int): Int {
    return MathHelper.clamp(index, 0, this.size - 1)
}