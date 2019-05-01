package aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.SimpleCounter

// The default implementation of the capability. Holds all the logic.
class ArborealAuraData {
    private val counter: SimpleCounter = SimpleCounter(ConfigAura.arborealAura.woodToBreak)

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
