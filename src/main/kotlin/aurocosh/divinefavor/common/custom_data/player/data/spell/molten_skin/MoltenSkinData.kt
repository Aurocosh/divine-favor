package aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.LimitedTimer

// The default implementation of the capability. Holds all the logic.
class MoltenSkinData {
    private val timer: LimitedTimer = LimitedTimer(ConfigSpell.moltenSkin.maxTimeOutsideLava)

    val ticks: Int
        get() = timer.getTicks()

    fun setMaxTime() {
        setTime(ConfigSpell.moltenSkin.maxTimeOutsideLava)
    }

    fun setTime(ticks: Int) {
        timer.setTicks(ticks)
    }

    fun resetTime() {
        timer.reset()
    }

    fun delay() {
        timer.setTicks(timer.getTicks() - ConfigSpell.moltenSkin.damageDelay)
    }

    fun tick(): Boolean {
        return timer.tick()
    }
}
