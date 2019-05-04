package aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye

import aurocosh.divinefavor.common.config.common.ConfigSpells
import net.minecraft.util.math.MathHelper

// The default implementation of the capability. Holds all the logic.
class EvilEyeData {
    var severity: Int = 1
        set(value) {
            field = MathHelper.clamp(value, ConfigSpells.evilEye.startingSeverity, ConfigSpells.evilEye.maxSeverity)
        }

    fun increaseSeverity(increase: Int) {
        severity += increase
    }
}
