package aurocosh.divinefavor.common.lib.coordinates

import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.collections.HashSet

open class FloodFiller {
    protected val explored = HashSet<BlockPos>()
    protected val expansionFront = ArrayDeque<BlockPos>()
    protected val result = ArrayList<BlockPos>()

    open fun reset(){
        explored.clear()
        expansionFront.clear()
        result.clear()
    }

    open fun floodFill(start: Collection<BlockPos>, expansionDirs: Collection<BlockPos>, predicate: (BlockPos) -> Boolean, limit: Int): List<BlockPos> {
        reset()
        explored.addAll(start)
        expansionFront.addAll(start)

        while (expansionFront.size > 0 && result.size < limit) {
            val nextPos = expansionFront.remove()
            if (!predicate.invoke(nextPos))
                continue
            result.add(nextPos)
            for (expansionDir in expansionDirs) {
                val neighbour = nextPos.add(expansionDir)
                if (!explored.contains(neighbour)) {
                    expansionFront.add(neighbour)
                    explored.add(neighbour)
                }
            }
        }
        return result
    }
}