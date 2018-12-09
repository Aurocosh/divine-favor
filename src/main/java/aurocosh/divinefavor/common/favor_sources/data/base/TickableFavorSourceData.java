package aurocosh.divinefavor.common.favor_sources.data.base;

public abstract class TickableFavorSourceData extends FavorSourceData {
    private final int tickRate;

    public TickableFavorSourceData(String favorName, int favorCount, String unlockAdvancements, String lockAdvancement, int tickRate) {
        super(favorName, favorCount, unlockAdvancements, lockAdvancement);
        this.tickRate = tickRate;
    }
}
