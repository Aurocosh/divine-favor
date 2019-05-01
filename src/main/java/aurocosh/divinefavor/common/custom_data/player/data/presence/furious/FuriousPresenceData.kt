package aurocosh.divinefavor.common.custom_data.player.data.presence.furious

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.SimpleCounter
import aurocosh.divinefavor.common.util.UtilRandom

class FuriousPresenceData {
    private val counter: SimpleCounter = SimpleCounter(ConfigPresence.furiousPresence.minMonstersToKill)

    var count: Int
        get() = counter.count
        set(count) {
            counter.count = count
        }

    fun count(): Boolean {
        return counter.count()
    }

    fun reset() {
        counter.setRequired(UtilRandom.nextInt(ConfigPresence.furiousPresence.minMonstersToKill, ConfigPresence.furiousPresence.maxMonstersToKill))
    }
}
