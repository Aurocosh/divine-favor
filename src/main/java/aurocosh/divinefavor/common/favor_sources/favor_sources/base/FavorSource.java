package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.CheckForNull;
import java.util.List;

public abstract class FavorSource extends IForgeRegistryEntry.Impl<FavorSource> {
    public final ModFavor favor;
    public final int favorCount;

    public final ResourceLocation unlockAdvancements;
    public final ResourceLocation lockAdvancement;

    public final List<FavorCondition> conditions;

    public FavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, List<FavorCondition> conditions) {
        this.favor = favor;
        this.favorCount = favorCount;
        this.unlockAdvancements = unlockAdvancements;
        this.lockAdvancement = lockAdvancement;
        this.conditions = conditions;
    }

    public boolean isConditionsMet(EntityPlayer player, @CheckForNull Object context){
        for (FavorCondition condition : conditions)
            if (!condition.isMet(player, context))
                return false;
        return true;
    }
}
