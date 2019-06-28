package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.lib.extensions.getSafe

open class EnumIndexer<T : Enum<T>>(val values: Array<T>) {
    operator fun get(index: Int) = values.getSafe(index)
    fun getMaxOrdinal() = values.size - 1
}
