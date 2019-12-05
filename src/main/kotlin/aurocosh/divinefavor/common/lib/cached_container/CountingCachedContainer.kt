package aurocosh.divinefavor.common.lib.cached_container

import aurocosh.divinefavor.common.lib.LoopedCounter

class CountingCachedContainer<T>(refreshRate: Int, generator: () -> T) : CachedContainer<T>(generator) {
    private var counter = LoopedCounter(refreshRate)

    override fun getValue(vararg state: Any, generator: () -> T): T {
        val forceRefresh = counter.tick()
        return super.getValue(forceRefresh, state, generator)
    }
}
