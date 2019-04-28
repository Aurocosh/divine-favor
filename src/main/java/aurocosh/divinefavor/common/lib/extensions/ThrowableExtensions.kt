package aurocosh.divinefavor.common.lib.extensions

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

fun <R> Throwable.multicatch(vararg classes: KClass<*>, block: () -> R): R {
    if (classes.any { this::class.isSubclassOf(it) }) {
        return block()
    } else throw this
}