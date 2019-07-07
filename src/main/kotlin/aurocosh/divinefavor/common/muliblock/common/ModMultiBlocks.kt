package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.common.muliblock.ModMultiBlock

object ModMultiBlocks {
    lateinit var altar_arbow: ModMultiBlock
    lateinit var altar_blizrabi: ModMultiBlock
    lateinit var altar_endererer: ModMultiBlock
    lateinit var altar_loon: ModMultiBlock
    lateinit var altar_materia: ModMultiBlock
    lateinit var altar_neblaze: ModMultiBlock
    lateinit var altar_redwind: ModMultiBlock
    lateinit var altar_romol: ModMultiBlock
    lateinit var altar_squarefury: ModMultiBlock
    lateinit var altar_timber: ModMultiBlock

    lateinit var soulbound_lectern_arbow: ModMultiBlock
    lateinit var soulbound_lectern_blizrabi: ModMultiBlock
    lateinit var soulbound_lectern_endererer: ModMultiBlock
    lateinit var soulbound_lectern_loon: ModMultiBlock
    lateinit var soulbound_lectern_materia: ModMultiBlock
    lateinit var soulbound_lectern_neblaze: ModMultiBlock
    lateinit var soulbound_lectern_redwind: ModMultiBlock
    lateinit var soulbound_lectern_romol: ModMultiBlock
    lateinit var soulbound_lectern_squarefury: ModMultiBlock
    lateinit var soulbound_lectern_timber: ModMultiBlock

    lateinit var snowman: ModMultiBlock
    lateinit var iron_golem: ModMultiBlock

    lateinit var bath_example_first: ModMultiBlock
    lateinit var bath_example_second: ModMultiBlock
    lateinit var bath_example_third: ModMultiBlock

    fun preInit() {
        altar_arbow = MultiblockLoader.load("altar", "arbow")
        altar_blizrabi = MultiblockLoader.load("altar", "blizrabi")
        altar_endererer = MultiblockLoader.load("altar", "endererer")
        altar_loon = MultiblockLoader.load("altar", "loon")
        altar_materia = MultiblockLoader.load("altar", "materia")
        altar_neblaze = MultiblockLoader.load("altar", "neblaze")
        altar_redwind = MultiblockLoader.load("altar", "redwind")
        altar_romol = MultiblockLoader.load("altar", "romol")
        altar_squarefury = MultiblockLoader.load("altar", "squarefury")
        altar_timber = MultiblockLoader.load("altar", "timber")

        soulbound_lectern_arbow = MultiblockLoader.load("soulbound_lectern", "arbow")
        soulbound_lectern_blizrabi = MultiblockLoader.load("soulbound_lectern", "blizrabi")
        soulbound_lectern_endererer = MultiblockLoader.load("soulbound_lectern", "endererer")
        soulbound_lectern_loon = MultiblockLoader.load("soulbound_lectern", "loon")
        soulbound_lectern_materia = MultiblockLoader.load("soulbound_lectern", "materia")
        soulbound_lectern_neblaze = MultiblockLoader.load("soulbound_lectern", "neblaze")
        soulbound_lectern_redwind = MultiblockLoader.load("soulbound_lectern", "redwind")
        soulbound_lectern_romol = MultiblockLoader.load("soulbound_lectern", "romol")
        soulbound_lectern_squarefury = MultiblockLoader.load("soulbound_lectern", "squarefury")
        soulbound_lectern_timber = MultiblockLoader.load("soulbound_lectern", "timber")

        snowman = MultiblockLoader.load("spawn_validation", "snowman")
        iron_golem = MultiblockLoader.load("spawn_validation", "iron_golem")

        bath_example_first = MultiblockLoader.load("bath_example", "first")
        bath_example_second = MultiblockLoader.load("bath_example", "second")
        bath_example_third = MultiblockLoader.load("bath_example", "third")
    }

    fun init() {
        PatchouliMultiblockAdapter.register(altar_arbow)
        PatchouliMultiblockAdapter.register(altar_blizrabi)
        PatchouliMultiblockAdapter.register(altar_endererer)
        PatchouliMultiblockAdapter.register(altar_loon)
        PatchouliMultiblockAdapter.register(altar_materia)
        PatchouliMultiblockAdapter.register(altar_neblaze)
        PatchouliMultiblockAdapter.register(altar_redwind)
        PatchouliMultiblockAdapter.register(altar_romol)
        PatchouliMultiblockAdapter.register(altar_squarefury)
        PatchouliMultiblockAdapter.register(altar_timber)

        PatchouliMultiblockAdapter.register(soulbound_lectern_arbow)
        PatchouliMultiblockAdapter.register(soulbound_lectern_blizrabi)
        PatchouliMultiblockAdapter.register(soulbound_lectern_endererer)
        PatchouliMultiblockAdapter.register(soulbound_lectern_loon)
        PatchouliMultiblockAdapter.register(soulbound_lectern_materia)
        PatchouliMultiblockAdapter.register(soulbound_lectern_neblaze)
        PatchouliMultiblockAdapter.register(soulbound_lectern_redwind)
        PatchouliMultiblockAdapter.register(soulbound_lectern_romol)
        PatchouliMultiblockAdapter.register(soulbound_lectern_squarefury)
        PatchouliMultiblockAdapter.register(soulbound_lectern_timber)

        PatchouliMultiblockAdapter.register(bath_example_first)
        PatchouliMultiblockAdapter.register(bath_example_second)
        PatchouliMultiblockAdapter.register(bath_example_third)
    }
}