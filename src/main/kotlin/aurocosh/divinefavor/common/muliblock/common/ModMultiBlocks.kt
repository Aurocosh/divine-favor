package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.common.ModCustomMultiBlocks.getMultiblock

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

    lateinit var goo_bath_small: ModMultiBlock
    lateinit var goo_bath_medium: ModMultiBlock
    lateinit var goo_bath_big: ModMultiBlock

    fun preInit() {
        altar_arbow = getMultiblock("altar", "arbow")
        altar_blizrabi = getMultiblock("altar", "blizrabi")
        altar_endererer = getMultiblock("altar", "endererer")
        altar_loon = getMultiblock("altar", "loon")
        altar_materia = getMultiblock("altar", "materia")
        altar_neblaze = getMultiblock("altar", "neblaze")
        altar_redwind = getMultiblock("altar", "redwind")
        altar_romol = getMultiblock("altar", "romol")
        altar_squarefury = getMultiblock("altar", "squarefury")
        altar_timber = getMultiblock("altar", "timber")

        soulbound_lectern_arbow = getMultiblock("soulbound_lectern", "arbow")
        soulbound_lectern_blizrabi = getMultiblock("soulbound_lectern", "blizrabi")
        soulbound_lectern_endererer = getMultiblock("soulbound_lectern", "endererer")
        soulbound_lectern_loon = getMultiblock("soulbound_lectern", "loon")
        soulbound_lectern_materia = getMultiblock("soulbound_lectern", "materia")
        soulbound_lectern_neblaze = getMultiblock("soulbound_lectern", "neblaze")
        soulbound_lectern_redwind = getMultiblock("soulbound_lectern", "redwind")
        soulbound_lectern_romol = getMultiblock("soulbound_lectern", "romol")
        soulbound_lectern_squarefury = getMultiblock("soulbound_lectern", "squarefury")
        soulbound_lectern_timber = getMultiblock("soulbound_lectern", "timber")

        snowman = getMultiblock("spawn_validation", "snowman")
        iron_golem = getMultiblock("spawn_validation", "iron_golem")

        bath_example_first = getMultiblock("bath_example", "first")
        bath_example_second = getMultiblock("bath_example", "second")
        bath_example_third = getMultiblock("bath_example", "third")

        goo_bath_small = getMultiblock("goo_bath", "small")
        goo_bath_medium = getMultiblock("goo_bath", "medium")
        goo_bath_big = getMultiblock("goo_bath", "big")
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

        PatchouliMultiblockAdapter.register(goo_bath_small)
        PatchouliMultiblockAdapter.register(goo_bath_medium)
        PatchouliMultiblockAdapter.register(goo_bath_big)
    }
}