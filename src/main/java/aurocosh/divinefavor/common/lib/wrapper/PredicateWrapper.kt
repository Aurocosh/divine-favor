package aurocosh.divinefavor.common.lib.wrapper

open class PredicateWrapper<T, K>(private val converter: (T) -> K, private val predicate: (K) -> Boolean) {

    fun test(value: T): Boolean {
        val converted = converter.invoke(value)
        return predicate.invoke(converted)
    }
}
