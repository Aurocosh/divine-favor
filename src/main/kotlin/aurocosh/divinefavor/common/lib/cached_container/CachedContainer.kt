package aurocosh.divinefavor.common.lib.cached_container

open class CachedContainer<T>(generator: () -> T) {
    private var cachedState: Array<out Any> = emptyArray()
    private var cachedValue: T = generator.invoke()

    open fun getValue(vararg state: Any, generator: () -> T) = getValue(false, state, generator)

    protected fun getValue(forceRefresh: Boolean, state: Array<out Any>, generator: () -> T): T {
        if (!forceRefresh && state contentEquals cachedState)
            return cachedValue
        cachedState = state
        cachedValue = generator.invoke()
        return cachedValue
    }
}
