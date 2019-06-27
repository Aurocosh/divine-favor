package aurocosh.divinefavor.common.muliblock

import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class MultiBlockConfiguration(val name: String, val primary: Boolean, val baseRelPosition: BlockPos, val controllerRelPosition: BlockPos, parts: List<MultiBlockPart>, val boundingBox: CuboidBoundingBox) {
    val parts: List<MultiBlockPart> = Collections.unmodifiableList(parts)

    val boundingBoxRelative: CuboidBoundingBox
        get() = boundingBox.subtract(controllerRelPosition)

    fun isValid(world: World, controller: BlockPos): Boolean {
        val multiBlockOrigin = controller.subtract(controllerRelPosition)
        for (part in parts)
            if (!part.isAllValid(world, multiBlockOrigin))
                return false
        return true
    }

    fun rotateClockwise(): MultiBlockConfiguration {
        val boundingBox = getPartsBoundingBox(parts)
        val sizeVector = boundingBox.sizeVector

        val partsNew = ArrayList<MultiBlockPart>(parts.size)
        for (part in parts) {
            val positionsNew = ArrayList<BlockPos>(part.positions.size)
            for (position in part.positions)
                positionsNew.add(BlockPos(position.z, position.y, sizeVector.x - position.x - 1))
            partsNew.add(MultiBlockPart(part.validator, positionsNew))
        }
        val controllerRelPositionNew = BlockPos(controllerRelPosition.z, controllerRelPosition.y, sizeVector.x - controllerRelPosition.x - 1)
        val boundingBoxNew = getPartsBoundingBox(partsNew)
        return MultiBlockConfiguration(name, false, baseRelPosition, controllerRelPositionNew, partsNew, boundingBoxNew)
    }

    private fun getPartsBoundingBox(parts: List<MultiBlockPart>): CuboidBoundingBox {
        var boundingBox = CuboidBoundingBox()
        for (part in parts)
            boundingBox = boundingBox.expandBoundingBox(part.positions)
        return boundingBox
    }
}
