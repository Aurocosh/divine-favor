package aurocosh.divinefavor.common.favor_sources.data.base;

public abstract class RegenFavorSourceData extends TickableFavorSourceData {
    private final int upperRegenLimit;

    public RegenFavorSourceData(String favorName, int favorCount, String unlockAdvancements, String lockAdvancement, int tickRate, int upperRegenLimit) {
        super(favorName, favorCount, unlockAdvancements, lockAdvancement, tickRate);
        this.upperRegenLimit = upperRegenLimit;
    }
}
