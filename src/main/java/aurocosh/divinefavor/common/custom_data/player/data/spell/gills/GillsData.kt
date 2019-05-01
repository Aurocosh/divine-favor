package aurocosh.divinefavor.common.custom_data.player.data.spell.gills

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.lib.LimitedTimer

// The default implementation of the capability. Holds all the logic.
class GillsData {
    private val timer: LimitedTimer = LimitedTimer(ConfigSpells.gills.maxTimeNotInWater)

    val ticks: Int
        get() = timer.getTicks()

    fun setMaxTime() {
        setTime(ConfigSpells.gills.maxTimeNotInWater)
    }

    fun setTime(ticks: Int) {
        timer.setTicks(ticks)
    }

    fun resetTime() {
        timer.reset()
    }

    fun delay() {
        timer.setTicks(timer.getTicks() - ConfigSpells.gills.damageDelay)
    }

    fun tick(): Boolean {
        return timer.tick()
    }
}
