package aurocosh.divinefavor.common.coordinate_generators


class CachedContainer<T>(generator: () -> T) {
    private var cachedState: Array<out Any> = emptyArray()
    private var cachedValue: T = generator.invoke()

    fun getValue(vararg state: Any, generator: () -> T): T {
        if (state contentEquals cachedState)
            return cachedValue
        cachedState = state
        cachedValue = generator.invoke()
        return cachedValue
    }
}
