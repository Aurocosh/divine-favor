package aurocosh.divinefavor.common.area

import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import net.minecraft.util.math.BlockPos
import java.util.*

class WorldArea {
    private val positions: MutableSet<BlockPos>
    private var boundingBox: CuboidBoundingBox

    init {
        positions = HashSet()
        boundingBox = CuboidBoundingBox()
    }

    fun addPositions(posList: List<BlockPos>) {
        positions.addAll(posList)
        refreshBoundingBox()
    }

    fun addPosition(pos: BlockPos) {
        positions.add(pos)
        refreshBoundingBox()
    }

    fun removePosition(pos: BlockPos) {
        positions.remove(pos)
        refreshBoundingBox()
    }

    fun clearPositions() {
        positions.clear()
        refreshBoundingBox()
    }

    private fun refreshBoundingBox() {
        boundingBox = CuboidBoundingBox.getBoundingBox(positions)
    }

    fun isApartOfArea(position: BlockPos): Boolean {
        return boundingBox.isCoordinateInside(position) && positions.contains(position)
    }
}
