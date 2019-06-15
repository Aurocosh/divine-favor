package aurocosh.divinefavor.common.coordinate_generators

import net.minecraft.util.math.BlockPos

private data class RenderState(val pos: BlockPos, val count: Int)

class ColumnCoordinateGenerator {
    private var lastRenderState = RenderState(BlockPos.ORIGIN, -1)
    private val cachedCoordinates: MutableList<BlockPos> = ArrayList()

    fun getCoordinates(blockPos: BlockPos, count: Int): List<BlockPos> {
        val renderState = RenderState(blockPos, count)
        if (renderState == lastRenderState)
            return cachedCoordinates
        lastRenderState = renderState

        cachedCoordinates.clear()
        var pos = blockPos
        for (i in 0 until count) {
            pos = pos.up()
            cachedCoordinates.add(pos)
        }
        return cachedCoordinates
    }
}
