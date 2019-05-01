package aurocosh.divinefavor.common.custom_data.player.data.aura.distorted

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

// The default implementation of the capability. Holds all the logic.
class DistortedAuraData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigAura.distortedAura.startingChance, ConfigAura.distortedAura.chanceIncrease)

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
