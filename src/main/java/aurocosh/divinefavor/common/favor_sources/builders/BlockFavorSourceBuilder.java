package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public abstract class BlockFavorSourceBuilder<T extends FavorSourceBuilder<T>> extends FavorSourceBuilder<T> {
    protected Set<ResourceLocation> validBlocks;

    public BlockFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
        this.validBlocks = new HashSet<>();
    }

    public T addBlock(ResourceLocation name){
        validBlocks.add(name);
        return (T) this;
    }
}
