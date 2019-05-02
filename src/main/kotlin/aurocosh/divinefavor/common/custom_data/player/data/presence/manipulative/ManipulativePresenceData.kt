package aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

class ManipulativePresenceData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigPresence.manipulativePresence.startingChance, ConfigPresence.manipulativePresence.chanceIncrease)

    var chance: Float
        get() = chanceCounter.getChance()
        set(chance) = chanceCounter.setChance(chance)

    fun reset() {
        chanceCounter.reset()
    }

    fun tryLuck(): Boolean {
        return chanceCounter.tryLuck()
    }
}
