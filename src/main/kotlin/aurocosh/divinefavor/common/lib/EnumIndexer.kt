package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.lib.extensions.getSafe

class EnumIndexer<T : Enum<T>>(val values: Array<T>) {
    operator fun get(index: Int): T {
        return values.getSafe(index)
    }
}
