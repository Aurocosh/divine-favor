package aurocosh.divinefavor.common.lib

enum class RotationDirection {
    Clockwise,
    Counter;

    companion object : IIndexedEnum<RotationDirection> {
        override val indexer: EnumIndexer<RotationDirection> = EnumIndexer(values())
    }
}