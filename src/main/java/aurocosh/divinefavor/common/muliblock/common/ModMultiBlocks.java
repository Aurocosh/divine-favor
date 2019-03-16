package aurocosh.divinefavor.common.muliblock.common;

import aurocosh.divinefavor.common.muliblock.ModMultiBlock;

public final class ModMultiBlocks {
    public static ModMultiBlock altar_arbow;
    public static ModMultiBlock altar_blizrabi;
    public static ModMultiBlock altar_endererer;
    public static ModMultiBlock altar_loon;
    public static ModMultiBlock altar_neblaze;
    public static ModMultiBlock altar_redwind;
    public static ModMultiBlock altar_romol;
    public static ModMultiBlock altar_squarefury;
    public static ModMultiBlock altar_timber;

    public static ModMultiBlock soulbound_lectern_arbow;
    public static ModMultiBlock soulbound_lectern_blizrabi;
    public static ModMultiBlock soulbound_lectern_endererer;
    public static ModMultiBlock soulbound_lectern_loon;
    public static ModMultiBlock soulbound_lectern_neblaze;
    public static ModMultiBlock soulbound_lectern_redwind;
    public static ModMultiBlock soulbound_lectern_romol;
    public static ModMultiBlock soulbound_lectern_squarefury;
    public static ModMultiBlock soulbound_lectern_timber;

    public static ModMultiBlock snowman;
    public static ModMultiBlock iron_golem;

    public static ModMultiBlock bath_example_first;
    public static ModMultiBlock bath_example_second;
    public static ModMultiBlock bath_example_third;

    public static void preInit() {
        altar_arbow = MultiblockLoader.load("altar", "arbow");
        altar_blizrabi = MultiblockLoader.load("altar", "blizrabi");
        altar_endererer = MultiblockLoader.load("altar", "endererer");
        altar_loon = MultiblockLoader.load("altar", "loon");
        altar_neblaze = MultiblockLoader.load("altar", "neblaze");
        altar_redwind = MultiblockLoader.load("altar", "redwind");
        altar_romol = MultiblockLoader.load("altar", "romol");
        altar_squarefury = MultiblockLoader.load("altar", "squarefury");
        altar_timber = MultiblockLoader.load("altar", "timber");

        soulbound_lectern_arbow = MultiblockLoader.load("soulbound_lectern", "arbow");
        soulbound_lectern_blizrabi = MultiblockLoader.load("soulbound_lectern", "blizrabi");
        soulbound_lectern_endererer = MultiblockLoader.load("soulbound_lectern", "endererer");
        soulbound_lectern_loon = MultiblockLoader.load("soulbound_lectern", "loon");
        soulbound_lectern_neblaze = MultiblockLoader.load("soulbound_lectern", "neblaze");
        soulbound_lectern_redwind = MultiblockLoader.load("soulbound_lectern", "redwind");
        soulbound_lectern_romol = MultiblockLoader.load("soulbound_lectern", "romol");
        soulbound_lectern_squarefury = MultiblockLoader.load("soulbound_lectern", "squarefury");
        soulbound_lectern_timber = MultiblockLoader.load("soulbound_lectern", "timber");

        snowman = MultiblockLoader.load("spawn_validation", "snowman");
        iron_golem = MultiblockLoader.load("spawn_validation", "iron_golem");

        bath_example_first = MultiblockLoader.load("bath_example", "first");
        bath_example_second = MultiblockLoader.load("bath_example", "second");
        bath_example_third = MultiblockLoader.load("bath_example", "third");
    }

    public static void init() {
        PatchouliMultiblockAdapter.register(altar_arbow);
        PatchouliMultiblockAdapter.register(altar_blizrabi);
        PatchouliMultiblockAdapter.register(altar_endererer);
        PatchouliMultiblockAdapter.register(altar_loon);
        PatchouliMultiblockAdapter.register(altar_neblaze);
        PatchouliMultiblockAdapter.register(altar_redwind);
        PatchouliMultiblockAdapter.register(altar_romol);
        PatchouliMultiblockAdapter.register(altar_squarefury);

        PatchouliMultiblockAdapter.register(soulbound_lectern_arbow);
        PatchouliMultiblockAdapter.register(soulbound_lectern_romol);
        PatchouliMultiblockAdapter.register(altar_timber);

        PatchouliMultiblockAdapter.register(bath_example_first);
        PatchouliMultiblockAdapter.register(bath_example_second);
        PatchouliMultiblockAdapter.register(bath_example_third);
    }
}