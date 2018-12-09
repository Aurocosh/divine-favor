package aurocosh.divinefavor.common.favor_sources.favor_sources.event;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BlockFavorSource extends FavorSource {
    private final Set<ResourceLocation> validBlocks;

    public BlockFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, Set<ResourceLocation> validBlocks) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement);
        this.validBlocks = Collections.unmodifiableSet(new HashSet<>(validBlocks));
    }

    public boolean isBlockCorrect(ResourceLocation name) {
        return validBlocks.contains(name);
    }
}
