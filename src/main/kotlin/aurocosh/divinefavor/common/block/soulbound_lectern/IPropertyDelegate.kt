package aurocosh.divinefavor.common.block.soulbound_lectern

import kotlin.reflect.KProperty

interface IPropertyDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}