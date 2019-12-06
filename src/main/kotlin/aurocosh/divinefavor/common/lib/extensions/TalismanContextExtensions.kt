package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.Quadruple
import aurocosh.divinefavor.common.lib.Quintuple

fun <T> CastContext.get(one: (CastContext) -> T): T {
    return one.invoke(this)
}

fun <T, K> CastContext.get(one: (CastContext) -> T, two: (CastContext) -> K): Pair<T, K> {
    return Pair(one.invoke(this), two.invoke(this))
}

fun <A, B, C> CastContext.get(one: (CastContext) -> A, two: (CastContext) -> B, three: (CastContext) -> C): Triple<A, B, C> {
    return Triple(one.invoke(this), two.invoke(this), three.invoke(this))
}

fun <A, B, C, D> CastContext.get(one: (CastContext) -> A, two: (CastContext) -> B, three: (CastContext) -> C, four: (CastContext) -> D): Quadruple<A, B, C, D> {
    return Quadruple(one.invoke(this), two.invoke(this), three.invoke(this), four.invoke(this))
}

fun <A, B, C, D, E> CastContext.get(one: (CastContext) -> A, two: (CastContext) -> B, three: (CastContext) -> C, four: (CastContext) -> D, five: (CastContext) -> E): Quintuple<A, B, C, D, E> {
    return Quintuple(one.invoke(this), two.invoke(this), three.invoke(this), four.invoke(this), five.invoke(this))
}
