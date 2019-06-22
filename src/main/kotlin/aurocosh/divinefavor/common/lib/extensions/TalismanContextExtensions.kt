package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.Quadruple
import aurocosh.divinefavor.common.lib.Quintuple

fun <T> TalismanContext.get(one: (TalismanContext) -> T): T {
    return one.invoke(this)
}

fun <T, K> TalismanContext.get(one: (TalismanContext) -> T, two: (TalismanContext) -> K): Pair<T, K> {
    return Pair(one.invoke(this), two.invoke(this))
}

fun <A, B, C> TalismanContext.get(one: (TalismanContext) -> A, two: (TalismanContext) -> B, three: (TalismanContext) -> C): Triple<A, B, C> {
    return Triple(one.invoke(this), two.invoke(this), three.invoke(this))
}

fun <A, B, C, D> TalismanContext.get(one: (TalismanContext) -> A, two: (TalismanContext) -> B, three: (TalismanContext) -> C, four: (TalismanContext) -> D): Quadruple<A, B, C, D> {
    return Quadruple(one.invoke(this), two.invoke(this), three.invoke(this), four.invoke(this))
}

fun <A, B, C, D, E> TalismanContext.get(one: (TalismanContext) -> A, two: (TalismanContext) -> B, three: (TalismanContext) -> C, four: (TalismanContext) -> D, five: (TalismanContext) -> E): Quintuple<A, B, C, D, E> {
    return Quintuple(one.invoke(this), two.invoke(this), three.invoke(this), four.invoke(this), five.invoke(this))
}
