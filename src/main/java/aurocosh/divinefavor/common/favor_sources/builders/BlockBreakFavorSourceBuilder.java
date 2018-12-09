package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.event.BlockBreakFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;

public class BlockBreakFavorSourceBuilder extends BlockFavorSourceBuilder<BlockBreakFavorSourceBuilder> {
    public BlockBreakFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
    }

    @Override
    public FavorSource create() {
        return new BlockBreakFavorSource(favor,favorCount,unlockAdvancements,lockAdvancement, validBlocks);
    }
}
