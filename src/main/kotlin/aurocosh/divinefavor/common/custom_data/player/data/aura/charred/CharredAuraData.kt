package aurocosh.divinefavor.common.custom_data.player.data.aura.charred

import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.lib.SimpleCounter

// The default implementation of the capability. Holds all the logic.
class CharredAuraData : SimpleCounter(ConfigAura.charredAura.firesToIgnite) {

    override fun setRequired(required: Int) {
        throw UnsupportedOperationException()
    }
}
