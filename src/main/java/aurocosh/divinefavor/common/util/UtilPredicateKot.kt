package aurocosh.divinefavor.common.util

class UtilPredicateKot {
    companion object {
        @SafeVarargs
        fun <T> and(vararg predicates: (T) -> Boolean): (T) -> Boolean {
            return { value -> predicates.all { predicate -> predicate.invoke(value) } }
        }

        @SafeVarargs
        fun <T> or(vararg predicates: (T) -> Boolean): (T) -> Boolean {
            return { value -> predicates.any { predicate -> predicate.invoke(value) } }
        }
    }
}
