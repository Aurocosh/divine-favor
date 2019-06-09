package aurocosh.divinefavor.common.custom_data.player.data.presence.towering

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.util.UtilRandom

class ToweringPresenceData {
    private val curseCounter: LoopedCounter = LoopedCounter(ConfigPresence.toweringPresence.minCurseDelay)

    var curseTime: Int
        get() = curseCounter.count
        set(ticks) {
            curseCounter.count = ticks
        }

    fun reset() {
        curseCounter.tickRate = ConfigPresence.toweringPresence.minCurseDelay
        curseCounter.reset()
    }

    fun tick(): Boolean {
        if (!curseCounter.tick())
            return false
        val nextDelay = UtilRandom.nextInt(ConfigPresence.toweringPresence.minCurseDelay, ConfigPresence.toweringPresence.maxCurseDelay)
        curseCounter.tickRate = nextDelay
        return true
    }
}
