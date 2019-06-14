package aurocosh.divinefavor.common.lib.extensions

fun Int.isEven(): Boolean {
    return this and 1 == 0
}

fun Int.isOdd(): Boolean = !this.isEven()



