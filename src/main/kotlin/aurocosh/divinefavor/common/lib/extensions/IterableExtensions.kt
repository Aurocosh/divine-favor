package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.math.MathHelper
import java.util.*
import kotlin.collections.Iterable
import kotlin.collections.List
import kotlin.collections.MutableCollection
import kotlin.collections.addAll
import kotlin.collections.all
import kotlin.collections.asSequence
import kotlin.collections.map
import kotlin.collections.mapTo
import kotlin.collections.ArrayList as ArrayList1


inline fun <T> Iterable<T>.split(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val selected = ArrayList<T>()
    val rejected = ArrayList<T>()
    for (value in this)
        if (predicate.invoke(value))
            selected.add(value)
        else
            rejected.add(value)
    return Pair(selected, rejected)
}

inline fun <T> Sequence<T>.split(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val selected = ArrayList<T>()
    val rejected = ArrayList<T>()
    for (value in this)
        if (predicate.invoke(value))
            selected.add(value)
        else
            rejected.add(value)
    return Pair(selected, rejected)
}

inline fun <T, K> Iterable<T>.filter(converter: (T) -> K, predicate: (K) -> Boolean): List<T> {
    return filterTo(ArrayList(), converter, predicate)
}

inline fun <T, C : MutableCollection<in T>, K> Iterable<T>.filterTo(destination: C, converter: (T) -> K, predicate: (K) -> Boolean): C {
    for (element in this)
        if (predicate(converter.invoke(element)))
            destination.add(element)
    return destination
}

fun <T, K> Sequence<T>.filter(converter: (T) -> K, predicate: (K) -> Boolean): Sequence<T> {
    return filter { predicate.invoke(converter.invoke(it)) }
}

fun <T, C : MutableCollection<in T>, K> Sequence<T>.filterTo(destination: C, converter: (T) -> K, predicate: (K) -> Boolean): C {
    return filterTo(destination) { predicate.invoke(converter.invoke(it)) }
}


inline fun <T, K> Iterable<T>.filterNot(converter: (T) -> K, predicate: (K) -> Boolean): List<T> {
    return filterNotTo(ArrayList(), converter, predicate)
}

inline fun <T, C : MutableCollection<in T>, K> Iterable<T>.filterNotTo(destination: C, converter: (T) -> K, predicate: (K) -> Boolean): C {
    for (element in this)
        if (!predicate(converter.invoke(element)))
            destination.add(element)
    return destination
}

fun <T, K> Sequence<T>.filterNot(converter: (T) -> K, predicate: (K) -> Boolean): Sequence<T> {
    return filterNot { predicate.invoke(converter.invoke(it)) }
}

fun <T, C : MutableCollection<in T>, K> Sequence<T>.filterNotTo(destination: C, converter: (T) -> K, predicate: (K) -> Boolean): C {
    return filterNotTo(destination) { predicate.invoke(converter.invoke(it)) }
}

fun <T> Iterable<T>.filterAnd(vararg predicate: (T) -> Boolean): List<T> {
    return filterAndTo(ArrayList(), predicate)
}

fun <T, C : MutableCollection<in T>> Iterable<T>.filterAnd(destination: C, vararg predicate: (T) -> Boolean): C {
    return filterAndTo(destination, predicate)
}

fun <T, C : MutableCollection<in T>> Iterable<T>.filterAndTo(destination: C, predicates: Array<out (T) -> Boolean>): C {
    for (element in this)
        if (predicates.all { predicate -> predicate.invoke(element) })
            destination.add(element)
    return destination
}

fun <T> Iterable<T>.selectRandom(count: Int): List<T> {
    return selectRandomTo(ArrayList(), count)
}

fun <T, C : MutableCollection<in T>> Iterable<T>.selectRandomTo(destination: C, count: Int): C {
    val list = ArrayList<T>()
    list.addAll(this)

    val realCount = MathHelper.clamp(count, 0, list.size)
    val dirsToRemove = list.size - realCount

    for (i in 0 until dirsToRemove) {
        val index = UtilRandom.nextIntExclusive(0, list.size)
        list.removeAt(index)
    }
    destination.addAll(list)
    return destination
}

inline fun <reified R> Sequence<*>.cast(): Sequence<R> {
    return map { it as R }
}

inline fun <reified R, C : MutableCollection<in R>> Sequence<*>.castTo(destination: C): C {
    return mapTo(destination) { it as R }
}

inline fun <reified R> Iterable<*>.cast(): List<R> {
    return map { it as R }
}

inline fun <reified R, C : MutableCollection<in R>> Iterable<*>.castTo(destination: C): C {
    return mapTo(destination) { it as R }
}

val <T> Iterable<T>.S: Sequence<T>
    get() = this.asSequence()

val <T> Array<out T>.S: Sequence<T>
    get() = this.asSequence()

inline fun <T> Iterable<T>.process(processor: (T) -> Any): List<T> {
    return this.map { processor.invoke(it); it }
}

fun <T> Sequence<T>.process(processor: (T) -> Any): Sequence<T> {
    return this.map { processor.invoke(it); it }
}


inline fun <T, K> Iterable<T>.mapPairs(processor: (T) -> K): List<Pair<T, K>> {
    return this.map { Pair(it, processor.invoke(it)) }
}

fun <T, K> Sequence<T>.mapPairs(processor: (T) -> K): Sequence<Pair<T, K>> {
    return this.map { Pair(it, processor.invoke(it)) }
}
