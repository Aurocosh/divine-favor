package aurocosh.divinefavor.common.favor_sources.favor_sources.event;

import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class BlockBreakFavorSource extends BlockFavorSource {
    public BlockBreakFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, Set<ResourceLocation> validBlocks) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement, validBlocks);
    }
}
