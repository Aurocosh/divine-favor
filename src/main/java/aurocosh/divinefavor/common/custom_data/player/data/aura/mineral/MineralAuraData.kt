package aurocosh.divinefavor.common.custom_data.player.data.aura.mineral

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.SimpleCounter

class MineralAuraData {
    private val counter: SimpleCounter = SimpleCounter(ConfigAura.mineralAura.stoneToBreak)

    var count: Int
        get() = counter.count
        set(count) {
            counter.count = count
        }

    fun count(): Boolean {
        return counter.count()
    }

    fun reset() {
        counter.reset()
    }
}
