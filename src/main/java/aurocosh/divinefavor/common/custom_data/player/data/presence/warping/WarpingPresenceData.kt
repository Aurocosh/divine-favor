package aurocosh.divinefavor.common.custom_data.player.data.presence.warping

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

class WarpingPresenceData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigPresence.warpingPresence.startingChance, ConfigPresence.warpingPresence.chanceIncrease)

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
