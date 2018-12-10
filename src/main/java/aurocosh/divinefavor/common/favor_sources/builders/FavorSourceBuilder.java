package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.conditions.BlockTypeCondition;
import aurocosh.divinefavor.common.favor_sources.conditions.MaxFavorCondition;
import aurocosh.divinefavor.common.favor_sources.conditions.TimeOfDayCondition;
import aurocosh.divinefavor.common.favor_sources.conditions.active_effect.ActiveEffectCondition;
import aurocosh.divinefavor.common.favor_sources.conditions.active_effect.DenyCondition;
import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public abstract class FavorSourceBuilder<T extends FavorSourceBuilder> {
    protected ModFavor favor;
    protected int favorCount;

    protected ResourceLocation unlockAdvancements;
    protected ResourceLocation lockAdvancement;

    protected List<FavorCondition> conditions;

    public FavorSourceBuilder(ModFavor favor, int favorCount) {
        this.favor = favor;
        this.favorCount = favorCount;
        conditions = new ArrayList<>();
    }

    public T addActiveEffectCondition(Potion potion, DenyCondition denyCondition) {
        conditions.add(new ActiveEffectCondition(potion, denyCondition));
        return (T) this;
    }

    public T addMaxFavorCondition(ModFavor favor, int favorCount) {
        conditions.add(new MaxFavorCondition(favor, favorCount));
        return (T) this;
    }

    public T addBlockEventCondition(Block... blocks) {
        Set<Block> blockSet = new HashSet<>(Arrays.asList(blocks));
        conditions.add(new BlockTypeCondition(Collections.unmodifiableSet(blockSet)));
        return (T) this;
    }

    public T addPeriodCondition(int start, int stop) {
        TimePeriod period = TimePeriod.fromHours(start, stop);
        conditions.add(new TimeOfDayCondition(period));
        return (T) this;
    }

    public T setUnlockAdvancements(ResourceLocation name) {
        unlockAdvancements = name;
        return (T) this;
    }

    public T setLockAdvancements(ResourceLocation name) {
        lockAdvancement = name;
        return (T) this;
    }

    public abstract FavorSource create();
}
