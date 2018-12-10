package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.TickableFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;

public class TickableFavorSourceBuilder extends FavorSourceBuilder<TickableFavorSourceBuilder> {
    protected int tickRate;

    public TickableFavorSourceBuilder(ModFavor favor, int favorCount, int tickRate) {
        super(favor, favorCount);
        this.tickRate = tickRate;
    }

    @Override
    public FavorSource create() {
        return new TickableFavorSource(favor, favorCount, unlockAdvancements, lockAdvancement, conditions, tickRate);
    }
}
