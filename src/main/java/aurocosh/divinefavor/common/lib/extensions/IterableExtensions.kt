package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.util.UtilMath
import aurocosh.divinefavor.common.util.UtilRandom
import java.util.*

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

public inline fun <T, K> Iterable<T>.filter(converter: (T) -> K, predicate: (K) -> Boolean): List<T> {
    return filterTo(ArrayList(), converter, predicate)
}

inline fun <T, C : MutableCollection<in T>, K> Iterable<T>.filterTo(destination: C, converter: (T) -> K, predicate: (K) -> Boolean): C {
    for (element in this)
        if (predicate(converter.invoke(element)))
            destination.add(element)
    return destination
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

    val realCount = UtilMath.clamp(count, 0, list.size)
    val dirsToRemove = list.size - realCount

    for (i in 0 until dirsToRemove) {
        val index = UtilRandom.nextIntExclusive(0, list.size)
        list.removeAt(index)
    }
    destination.addAll(list)
    return destination
}
