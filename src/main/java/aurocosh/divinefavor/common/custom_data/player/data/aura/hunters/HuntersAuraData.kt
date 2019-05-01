package aurocosh.divinefavor.common.custom_data.player.data.aura.hunters

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter

// The default implementation of the capability. Holds all the logic.
class HuntersAuraData {
    private val chanceCounter: IncrementalChanceCounter = IncrementalChanceCounter(ConfigAura.huntersAura.startingChance, ConfigAura.huntersAura.chanceIncrease)

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
