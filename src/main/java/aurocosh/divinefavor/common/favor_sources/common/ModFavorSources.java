package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.favor_sources.builders.EventFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.builders.TickableFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.conditions.active_effect.DenyCondition;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorEventType;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.RegistryMap;
import net.minecraft.init.Blocks;

public final class ModFavorSources {
    private static RegistryMap<FavorSource> favorSources = new RegistryMap<>();

    public static void preInit() {
        favorSources.register(new EventFavorSourceBuilder(ModFavors.favor_of_timber, 1, FavorEventType.BLOCK_BREAK)
                .addBlockEventCondition(Blocks.LOG, Blocks.PLANKS)
                .addActiveEffectCondition(ModPotions.wooden_punch, DenyCondition.DENY_IF_ACTIVE)
                .addMaxFavorCondition(ModFavors.favor_of_timber,60)
                .create());
        favorSources.register(new EventFavorSourceBuilder(ModFavors.favor_of_allfire, 1, FavorEventType.BLOCK_PLACE)
                .addBlockEventCondition(Blocks.FIRE)
                .create());
        favorSources.register(new TickableFavorSourceBuilder(ModFavors.favor_of_timber, 1, 100)
                .addMaxFavorCondition(ModFavors.favor_of_timber, 20)
                .addPeriodCondition(6,12)
                .create());
    }
}