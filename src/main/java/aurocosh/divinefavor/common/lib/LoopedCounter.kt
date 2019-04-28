package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilMath

class LoopedCounter {
    private var tickRate: Int = 0
    private var currentTicks: Int = 0

    var count: Int
        get() = currentTicks
        set(ticks) {
            currentTicks = UtilMath.clamp(ticks, 0, tickRate)
        }

    val isFinished: Boolean
        get() = currentTicks >= tickRate

    constructor() {
        tickRate = 1
        currentTicks = 0
    }

    constructor(tickRate: Int) {
        this.tickRate = tickRate
        currentTicks = 0
    }

    fun getTickRate(): Int {
        return tickRate
    }

    fun setTickRate(tickRate: Int) {
        this.tickRate = Math.max(1, Math.abs(tickRate))
    }

    fun reset() {
        currentTicks = 0
    }

    fun tick(): Boolean {
        if (currentTicks < tickRate) {
            currentTicks++
            return false
        } else {
            currentTicks = 0
            return true
        }
    }
}
