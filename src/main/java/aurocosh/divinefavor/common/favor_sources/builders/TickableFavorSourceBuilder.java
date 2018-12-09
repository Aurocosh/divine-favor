package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favors.ModFavor;

public abstract class TickableFavorSourceBuilder <T extends FavorSourceBuilder<T>> extends FavorSourceBuilder<T>{
    protected int tickRate;

    public TickableFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
        tickRate = 400;
    }

    public T setTickRate(int tickRate){
        this.tickRate = tickRate;
        return (T) this;
    }
}
