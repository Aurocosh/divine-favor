package aurocosh.divinefavor.common.custom_data.player.data.presence.industrious

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.SimpleCounter

class IndustriousPresenceData {
    private val counter: SimpleCounter = SimpleCounter(ConfigPresence.industriousPresence.oreToBreak)

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
