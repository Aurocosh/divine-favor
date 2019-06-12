package aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.GlobalBlockPos
import java.util.*

// The default implementation of the capability. Holds all the logic.
class PearlCrumbsData {
    private val positions: Deque<GlobalBlockPos>

    var allPositions: List<GlobalBlockPos>
        get() = ArrayList(positions)
        set(positions) {
            this.positions.clear()
            this.positions.addAll(positions)
        }

    init {
        positions = ArrayDeque()
    }

    fun popGlobalPosition(): GlobalBlockPos {
        return positions.pop()
    }

    fun pushGlobalPosition(pos: GlobalBlockPos) {
        positions.push(pos)
        if (positions.size > ConfigSpell.pearlCrumbs.maxPositionsSaved)
            positions.removeLast()
    }

    fun hasPositions(): Boolean {
        return !positions.isEmpty()
    }
}
