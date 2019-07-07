package aurocosh.divinefavor.common.custom_data.player.data.materia

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

// The default implementation of the capability. Holds all the logic.
class MaterialPresenceData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigPresence.materialPresence.startingChance, ConfigPresence.materialPresence.chanceIncrease)

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
