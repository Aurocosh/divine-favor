package aurocosh.divinefavor.common.lib

import net.minecraft.util.EnumFacing

data class FaceDiagonalOrientation(val firstCorner: CornerOrientation, val secondCorner: CornerOrientation, val thirdCorner: CornerOrientation) {
    constructor() : this(CornerOrientation.UpNorthWest, CornerOrientation.UpNorthEast, CornerOrientation.UpSouthEast)

    fun mirror(axis: EnumFacing.Axis): FaceDiagonalOrientation {
        val firstMirrored = firstCorner.mirror(axis)
        val secondMirrored = secondCorner.mirror(axis)
        val thirdMirrored = thirdCorner.mirror(axis)
        return FaceDiagonalOrientation(firstMirrored, secondMirrored, thirdMirrored)
    }

    fun rotate(axis: EnumFacing.Axis, direction: RotationDirection, count: Int = 1): FaceDiagonalOrientation {
        val firstRotated = firstCorner.rotate(axis, direction, count)
        val secondRotated = secondCorner.rotate(axis, direction, count)
        val thirdRotated = thirdCorner.rotate(axis, direction, count)
        return FaceDiagonalOrientation(firstRotated, secondRotated, thirdRotated)
    }
}