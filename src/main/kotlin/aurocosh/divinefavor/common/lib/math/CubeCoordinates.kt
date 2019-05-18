package aurocosh.divinefavor.common.lib.math

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.util.math.BlockPos

class CubeCoordinates {
    val lowerCorner: BlockPos
    val upperCorner: BlockPos

    val sizeVector: BlockPos
        get() = BlockPos(sizeX, sizeY, sizeZ)

    val sizeX: Int
        get() = upperCorner.x - lowerCorner.x + 1

    val sizeY: Int
        get() = upperCorner.y - lowerCorner.y + 1

    val sizeZ: Int
        get() = upperCorner.z - lowerCorner.z + 1

    val allPositionsInside: List<BlockPos>
        get() {
            val xSize = sizeX
            val ySize = sizeY
            val zSize = sizeZ

            val positionCount = xSize * ySize * zSize
            val positions = ArrayList<BlockPos>(positionCount)
            for (x in 0 until xSize) {
                for (y in 0 until ySize) {
                    for (z in 0 until zSize) {
                        val shift = BlockPos(x, y, z)
                        positions.add(lowerCorner.add(shift))
                    }
                }
            }
            return positions
        }

    constructor() {
        lowerCorner = BlockPos.ORIGIN
        upperCorner = BlockPos.ORIGIN
    }

    constructor(firestCorner: BlockPos, secondCorner: BlockPos) {
        val lowerX = Math.min(firestCorner.x, secondCorner.x)
        val lowerY = Math.min(firestCorner.y, secondCorner.y)
        val lowerZ = Math.min(firestCorner.z, secondCorner.z)

        val upperX = Math.max(firestCorner.x, secondCorner.x)
        val upperY = Math.max(firestCorner.y, secondCorner.y)
        val upperZ = Math.max(firestCorner.z, secondCorner.z)

        this.lowerCorner = BlockPos(lowerX, lowerY, lowerZ)
        this.upperCorner = BlockPos(upperX, upperY, upperZ)
    }

    fun add(vector: BlockPos): CubeCoordinates {
        val lower = lowerCorner.add(vector)
        val upper = upperCorner.add(vector)
        return CubeCoordinates(lower, upper)
    }

    fun subtract(vector: BlockPos): CubeCoordinates {
        return add(BlockPos.ORIGIN.subtract(vector))
    }

    fun isCoordinateInside(coordinate: BlockPos): Boolean {
        return (lowerCorner.x <= coordinate.x && coordinate.x <= upperCorner.x
                && lowerCorner.y <= coordinate.y && coordinate.y <= upperCorner.y
                && lowerCorner.z <= coordinate.z && coordinate.z <= upperCorner.z)
    }

    fun expandBoundingBox(positions: List<BlockPos>): CubeCoordinates {
        return getBoundingBox(lowerCorner, upperCorner, positions)
    }

    companion object {

        fun getBoundingBox(positions: Collection<BlockPos>): CubeCoordinates {
            val min = BlockPosConstants.MAX
            val max = BlockPosConstants.MIN
            return getBoundingBox(min, max, positions)
        }

        fun getBoundingBox(currentMin: BlockPos, currentMax: BlockPos, positions: Collection<BlockPos>): CubeCoordinates {
            var min = currentMin
            var max = currentMax
            if (positions.isEmpty())
                return CubeCoordinates()
            for (pos in positions) {
                min = UtilBlockPos.getMinCoordinates(min, pos)
                max = UtilBlockPos.getMaxCoordinates(max, pos)
            }
            return CubeCoordinates(min, max)
        }
    }
}
