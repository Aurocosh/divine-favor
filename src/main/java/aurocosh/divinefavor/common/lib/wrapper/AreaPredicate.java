package aurocosh.divinefavor.common.lib.wrapper;

import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class AreaPredicate<T> extends PredicateWrapper<BlockPos, T> {
    private final int matchesRequired;
    private final List<BlockPos> areaShifts;

    public AreaPredicate(Function<BlockPos, T> converter, Predicate<T> predicate, List<BlockPos> areaShifts, int matchesRequired) {
        super(converter, predicate);
        this.areaShifts = areaShifts;
        this.matchesRequired = Math.min(matchesRequired, areaShifts.size());
    }

    public boolean test(BlockPos value) {
        int requirements = matchesRequired;
        for (BlockPos areaShift : areaShifts) {
            BlockPos pos = value.add(areaShift);
            if (super.test(pos)) {
                requirements -= 1;
                if (requirements <= 0)
                    return true;
            }
        }

        return false;
    }
}
