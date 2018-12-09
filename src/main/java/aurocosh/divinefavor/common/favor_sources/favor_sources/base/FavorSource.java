package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class FavorSource extends IForgeRegistryEntry.Impl<FavorSource> {
    public final ModFavor favor;
    public final int favorCount;

    public final ResourceLocation unlockAdvancements;
    public final ResourceLocation lockAdvancement;

    public FavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement) {
        this.favor = favor;
        this.favorCount = favorCount;
        this.unlockAdvancements = unlockAdvancements;
        this.lockAdvancement = lockAdvancement;
    }
}
