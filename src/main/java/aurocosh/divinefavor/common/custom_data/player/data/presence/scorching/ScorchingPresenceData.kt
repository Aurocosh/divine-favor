package aurocosh.divinefavor.common.custom_data.player.data.presence.scorching

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

class ScorchingPresenceData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigPresence.scorchingPresence.startingChance, ConfigPresence.scorchingPresence.chanceIncrease)

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
