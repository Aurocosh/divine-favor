package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilMath

class LimitedTimer {
    private var limit: Int = 0
    private var ticks: Int = 0

    constructor() {
        limit = 1
        ticks = 0
    }

    constructor(limit: Int) {
        this.limit = limit
        ticks = 0
    }

    fun reset() {
        this.ticks = 0
    }

    fun setLimit(limit: Int) {
        this.limit = Math.max(1, Math.abs(limit))
    }

    fun setTicks(ticks: Int) {
        this.ticks = UtilMath.clamp(ticks, 0, limit)
    }

    fun getTicks(): Int {
        return ticks
    }

    fun tick(): Boolean {
        if (ticks >= limit)
            return true
        ticks++
        return false
    }
}
