package aurocosh.divinefavor.common.lib.wrapper

open class ConvertingPredicate<T, K>(private val converter: (T) -> K, private val predicate: (K) -> Boolean) {
    open fun invoke(value: T): Boolean {
        val converted = converter.invoke(value)
        return predicate.invoke(converted)
    }
}
