package aurocosh.divinefavor.common.custom_data.player.data.favor

import aurocosh.divinefavor.common.item.contract.ItemFavorContract
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.math.MathHelper

class SpiritStatus(private val spirit: ModSpirit) {
    var regen: Int = 0
        private set
    var minLimit: Int = 0
        private set
    var maxLimit: Int = 0
        private set
    var isInformActivity: Boolean = false
        private set

    init {
        reset()
    }

    fun reset() {
        regen = spirit.favorRegen
        minLimit = spirit.favorMin
        maxLimit = spirit.favorMax
        isInformActivity = false
    }

    fun addStats(contract: ItemFavorContract) {
        regen += contract.regen
        minLimit += contract.min
        maxLimit += contract.max
        isInformActivity = isInformActivity || contract.isInformActivity
    }

    fun clamp(value: Int): Int {
        return MathHelper.clamp(value, minLimit, maxLimit)
    }
}
