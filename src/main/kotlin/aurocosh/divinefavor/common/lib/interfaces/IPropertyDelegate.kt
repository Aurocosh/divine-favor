package aurocosh.divinefavor.common.lib.interfaces

import kotlin.reflect.KProperty

interface IPropertyDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}