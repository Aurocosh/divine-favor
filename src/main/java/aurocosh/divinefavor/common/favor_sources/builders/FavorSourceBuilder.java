package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;

public abstract class FavorSourceBuilder<T extends FavorSourceBuilder<T>> {
    protected ModFavor favor;
    protected int favorCount;

    protected ResourceLocation unlockAdvancements;
    protected ResourceLocation lockAdvancement;

    public FavorSourceBuilder(ModFavor favor, int favorCount) {
        this.favor = favor;
        this.favorCount = favorCount;
    }

    public T setUnlockAdvancements(ResourceLocation name){
        unlockAdvancements = name;
        return (T) this;
    }

    public T setLockAdvancements(ResourceLocation name){
        lockAdvancement = name;
        return (T) this;
    }

    public abstract FavorSource create();
}
