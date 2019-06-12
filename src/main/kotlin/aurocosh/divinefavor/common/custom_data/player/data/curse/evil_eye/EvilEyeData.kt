package aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye

import aurocosh.divinefavor.common.config.common.ConfigSpell
import net.minecraft.util.math.MathHelper

// The default implementation of the capability. Holds all the logic.
class EvilEyeData {
    var severity: Int = 1
        set(value) {
            field = MathHelper.clamp(value, ConfigSpell.evilEye.startingSeverity, ConfigSpell.evilEye.maxSeverity)
        }

    fun increaseSeverity(increase: Int) {
        severity += increase
    }
}
