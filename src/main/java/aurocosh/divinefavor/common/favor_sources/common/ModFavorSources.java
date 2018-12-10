package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.effect.common.ModPotions;
import aurocosh.divinefavor.common.favor_sources.builders.EventFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.builders.TickableFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.conditions.active_effect.DenyCondition;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorEventType;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavors;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModFavorSources {
    private static Map<ResourceLocation, FavorSource> favorSourceMap = new HashMap<>();

    public static void preInit() {
        register(new EventFavorSourceBuilder(ModFavors.favor_of_timber, 1, FavorEventType.BLOCK_BREAK)
                .addBlockEventCondition(Blocks.LOG, Blocks.PLANKS)
                .addActiveEffectCondition(ModPotions.wooden_punch, DenyCondition.DENY_IF_ACTIVE)
                .addMaxFavorCondition(ModFavors.favor_of_timber,60)
                .create());
        register(new EventFavorSourceBuilder(ModFavors.favor_of_allfire, 1, FavorEventType.BLOCK_PLACE)
                .addBlockEventCondition(Blocks.FIRE)
                .create());
        register(new TickableFavorSourceBuilder(ModFavors.favor_of_timber, 1, 100)
                .addMaxFavorCondition(ModFavors.favor_of_timber, 20)
                .addPeriodCondition(6,12)
                .create());
    }

    public static FavorSource register(FavorSource favorSource) {
        favorSourceMap.put(favorSource.getRegistryName(), favorSource);
        FavorSourceManager.addFavorSource(favorSource);
        return favorSource;
    }
}