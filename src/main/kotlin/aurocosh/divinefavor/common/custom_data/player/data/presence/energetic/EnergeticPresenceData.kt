package aurocosh.divinefavor.common.custom_data.player.data.presence.energetic

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.SimpleCounter

// The default implementation of the capability. Holds all the logic.
class EnergeticPresenceData {
    private val counter: SimpleCounter = SimpleCounter(ConfigPresence.energeticPresence.timeToRunOnWater)

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
