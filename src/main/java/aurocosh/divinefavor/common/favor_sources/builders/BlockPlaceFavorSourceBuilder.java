package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.event.BlockPlaceFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;

public class BlockPlaceFavorSourceBuilder extends BlockFavorSourceBuilder<BlockPlaceFavorSourceBuilder> {
    public BlockPlaceFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
    }

    @Override
    public FavorSource create() {
        return new BlockPlaceFavorSource(favor,favorCount,unlockAdvancements,lockAdvancement, validBlocks);
    }
}
