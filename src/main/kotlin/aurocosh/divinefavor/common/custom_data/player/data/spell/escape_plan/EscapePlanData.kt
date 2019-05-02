package aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan

import aurocosh.divinefavor.common.lib.GlobalBlockPos

// The default implementation of the capability. Holds all the logic.
class EscapePlanData {
    var globalPosition: GlobalBlockPos? = null

    init {
        globalPosition = GlobalBlockPos(0, 0, 0, 1)
    }
}
