package aurocosh.divinefavor.common.lib

interface IIndexedEnum<T : Enum<T>> {
    val indexer: EnumIndexer<T>
    operator fun get(index: Int) = indexer[index]
    fun getMaxOrdinal() = indexer.getMaxOrdinal()
}