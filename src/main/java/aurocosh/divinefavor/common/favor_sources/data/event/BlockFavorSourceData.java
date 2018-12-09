package aurocosh.divinefavor.common.favor_sources.data.event;

import aurocosh.divinefavor.common.favor_sources.data.base.FavorSourceData;

public class BlockFavorSourceData extends FavorSourceData {
    private String[] validBlockNames;

    public BlockFavorSourceData(String favorName, int favorCount, String unlockAdvancements, String lockAdvancement, String[] validBlockNames) {
        super(favorName, favorCount, unlockAdvancements, lockAdvancement);
        this.validBlockNames = validBlockNames;
    }
}
