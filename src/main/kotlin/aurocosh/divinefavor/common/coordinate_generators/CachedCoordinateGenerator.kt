package aurocosh.divinefavor.common.coordinate_generators

import net.minecraft.util.math.BlockPos


abstract class CachedCoordinateGenerator {
    private var cachedParameters: Array<out Any> = emptyArray()
    protected var cachedCoordinates: List<BlockPos> = ArrayList()

    fun isCached(vararg parameters: Any): Boolean {
        if (parameters contentEquals cachedParameters)
            return true
        cachedParameters = parameters
        return false
    }
}
