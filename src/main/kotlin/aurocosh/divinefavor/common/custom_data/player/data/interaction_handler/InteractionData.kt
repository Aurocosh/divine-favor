package aurocosh.divinefavor.common.custom_data.player.data.interaction_handler

import net.minecraft.util.math.BlockPos
import java.util.*

// The default implementation of the capability. Holds all the logic.
class InteractionData {

    var lastClickedPositions: List<BlockPos>
        get() = Collections.unmodifiableList(CLICKED_BLOCKS)
        set(positions) {
            CLICKED_BLOCKS.clear()
            val posToAdd = Math.min(positions.size, MAX_BLOCKS_REMEMBERED)
            CLICKED_BLOCKS.addAll(positions.take(posToAdd))
        }

    fun recordLastClickedPosition(pos: BlockPos) {
        if (CLICKED_BLOCKS.contains(pos))
            return
        CLICKED_BLOCKS.add(pos)
        if (CLICKED_BLOCKS.size > MAX_BLOCKS_REMEMBERED)
            CLICKED_BLOCKS.remove()
    }

    fun wasPositionClicked(pos: BlockPos): Boolean {
        return CLICKED_BLOCKS.contains(pos)
    }

    companion object {
        private const val MAX_BLOCKS_REMEMBERED = 8
        private val CLICKED_BLOCKS = LinkedList<BlockPos>()
    }
}
