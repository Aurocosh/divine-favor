package aurocosh.divinefavor.common.favor_sources.data.base;

public abstract class FavorSourceData {
    public final String favorName;
    public final int favorCount;

    public final String unlockAdvancements;
    public final String lockAdvancement;

    public FavorSourceData(String favorName, int favorCount, String unlockAdvancements, String lockAdvancement) {
        this.favorName = favorName;
        this.favorCount = favorCount;
        this.unlockAdvancements = unlockAdvancements;
        this.lockAdvancement = lockAdvancement;
    }
}
