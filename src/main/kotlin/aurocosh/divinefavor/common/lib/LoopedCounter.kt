package aurocosh.divinefavor.common.lib

import net.minecraft.util.math.MathHelper

class LoopedCounter {
    var tickRate: Int = 0
        set(value) {
            field = Math.max(1, Math.abs(value))
        }

    var count: Int = 0
        set(value) {
            field = MathHelper.clamp(value, 0, tickRate)
        }

    val isFinished: Boolean
        get() = count >= tickRate

    constructor() {
        tickRate = 1
        count = 0
    }

    constructor(tickRate: Int) {
        this.tickRate = tickRate
        count = 0
    }


    fun reset() {
        count = 0
    }

    fun tick(): Boolean {
        if (count < tickRate) {
            count++
            return false
        } else {
            count = 0
            return true
        }
    }
}
