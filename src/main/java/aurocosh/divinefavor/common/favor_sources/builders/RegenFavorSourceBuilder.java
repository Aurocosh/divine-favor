package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favors.ModFavor;

public abstract class RegenFavorSourceBuilder<T extends FavorSourceBuilder<T>> extends TickableFavorSourceBuilder<T>{
    protected int upperRegenLimit;

    public RegenFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
        upperRegenLimit = 10;
    }

    public T setUpperRegenLimit(int upperRegenLimit){
        this.upperRegenLimit = upperRegenLimit;
        return (T) this;
    }
}
