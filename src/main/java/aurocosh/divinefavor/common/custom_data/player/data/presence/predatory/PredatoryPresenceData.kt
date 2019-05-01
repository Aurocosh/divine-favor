package aurocosh.divinefavor.common.custom_data.player.data.presence.predatory

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.SimpleCounter
import aurocosh.divinefavor.common.util.UtilRandom

class PredatoryPresenceData {
    private val counter: SimpleCounter = SimpleCounter(ConfigPresence.predatoryPresence.minMonstersToKill)

    var count: Int
        get() = counter.count
        set(count) {
            counter.count = count
        }

    fun count(): Boolean {
        return counter.count()
    }

    fun reset() {
        counter.setRequired(UtilRandom.nextInt(ConfigPresence.predatoryPresence.minMonstersToKill, ConfigPresence.predatoryPresence.maxMonstersToKill))
    }
}
