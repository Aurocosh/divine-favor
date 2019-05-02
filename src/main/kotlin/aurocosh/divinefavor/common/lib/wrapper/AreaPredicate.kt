package aurocosh.divinefavor.common.lib.wrapper

import net.minecraft.util.math.BlockPos

open class AreaPredicate<T>(converter: (BlockPos) -> T, predicate: (T) -> Boolean, private val areaShifts: List<BlockPos>, matchesRequired: Int) : ConvertingPredicate<BlockPos, T>(converter, predicate) {
    private val matchesRequired: Int = Math.min(matchesRequired, areaShifts.size)

    override fun invoke(value: BlockPos): Boolean {
        var requirements = matchesRequired
        for (areaShift in areaShifts) {
            val pos = value.add(areaShift)
            if (super.invoke(pos)) {
                requirements -= 1
                if (requirements <= 0)
                    return true
            }
        }

        return false
    }
}
