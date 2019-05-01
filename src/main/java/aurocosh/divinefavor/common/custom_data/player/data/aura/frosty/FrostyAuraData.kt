package aurocosh.divinefavor.common.custom_data.player.data.aura.frosty

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.SimpleCounter

// The default implementation of the capability. Holds all the logic.
class FrostyAuraData {
    private val counter: SimpleCounter = SimpleCounter(ConfigAura.frostyAura.timeInSnowBiome)

    var count: Int
        get() = counter.count
        set(count) {
            counter.count = count
        }

    fun count(): Boolean {
        return counter.count()
    }

    fun reset() {
        counter.reset()
    }
}
