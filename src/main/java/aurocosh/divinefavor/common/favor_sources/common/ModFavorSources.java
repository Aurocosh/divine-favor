package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.builders.BlockBreakFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.builders.BlockPlaceFavorSourceBuilder;
import aurocosh.divinefavor.common.favor_sources.builders.TimeOfDayFavorSourceBuilder;
import aurocosh.divinefavor.common.favors.ModFavors;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModFavorSources {
    private static Map<ResourceLocation, FavorSource> favorSourceMap = new HashMap<>();

    public static void preInit() {
        register(new BlockBreakFavorSourceBuilder(ModFavors.favor_of_timber, 1)
                .addBlock(Blocks.LOG.getRegistryName())
                .addBlock(Blocks.PLANKS.getRegistryName())
                .create());
        register(new BlockPlaceFavorSourceBuilder(ModFavors.favor_of_allfire, 1)
                .addBlock(Blocks.FIRE.getRegistryName())
                .create());
        register(new TimeOfDayFavorSourceBuilder(ModFavors.favor_of_timber, 1)
                .setTickRate(100)
                .setUpperRegenLimit(20)
                .setPeriod(6,12)
                .create());
    }



    public static FavorSource register(FavorSource favorSource) {
        favorSourceMap.put(favorSource.getRegistryName(), favorSource);
        FavorSourceManager.addFavorSource(favorSource);
        return favorSource;
    }
}