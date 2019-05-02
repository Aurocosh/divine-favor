package aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.lib.LoopedCounter
import java.util.*

// The default implementation of the capability. Holds all the logic.
class ArmorCorrosionData {
    private val corrodedSlots: MutableList<Int> = ArrayList()
    private val loopedCounter: LoopedCounter = LoopedCounter(ConfigArrow.armorCorrosion.corrosionRate)

    var corrodedArmorSlots: List<Int>
        get() = Collections.unmodifiableList(corrodedSlots)
        set(slots) {
            corrodedSlots.clear()
            corrodedSlots.addAll(slots)
        }

    val isCorrosionNeeded: Boolean
        get() = loopedCounter.tick()

    fun removeAllCorrosion() {
        corrodedSlots.clear()
    }

    fun addCorrosionToArmorSlot(slot: Int) {
        assert(slot in 1..2)
        for (corrodedSlot in corrodedSlots)
            if (corrodedSlot == slot)
                return
        corrodedSlots.add(slot)
    }

    fun removeCorrosionFromArmorSlot(slot: Int) {
        corrodedSlots.remove(Integer.valueOf(slot))
    }

    fun nothingToCorrode(): Boolean {
        return corrodedSlots.size == 0
    }
}
