package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilArray

class EnumIndexer<T : Enum<T>>(val values: Array<T>) {

    operator fun get(index: Int): T {
        return values[clampIndex(index)]
    }

    fun clampIndex(index: Int): Int {
        return UtilArray.clampIndex(values, index)
    }
}
