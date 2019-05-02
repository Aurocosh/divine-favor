package aurocosh.divinefavor.common.item.calling_stones

import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.spirit.ModSpirits

object ModCallingStones {
    lateinit var calling_stone_arbow: ItemCallingStone
    lateinit var calling_stone_blizrabi: ItemCallingStone
    lateinit var calling_stone_endererer: ItemCallingStone
    lateinit var calling_stone_loon: ItemCallingStone
    lateinit var calling_stone_neblaze: ItemCallingStone
    lateinit var calling_stone_redwind: ItemCallingStone
    lateinit var calling_stone_romol: ItemCallingStone
    lateinit var calling_stone_squarefury: ItemCallingStone
    lateinit var calling_stone_timber: ItemCallingStone

    fun preInit() {
        calling_stone_arbow = ItemCallingStone("arbow", ModSpirits.arbow, ModMultiBlocks.altar_arbow)
        calling_stone_blizrabi = ItemCallingStone("blizrabi", ModSpirits.blizrabi, ModMultiBlocks.altar_blizrabi)
        calling_stone_endererer = ItemCallingStone("endererer", ModSpirits.endererer, ModMultiBlocks.altar_endererer)
        calling_stone_loon = ItemCallingStone("loon", ModSpirits.loon, ModMultiBlocks.altar_loon)
        calling_stone_neblaze = ItemCallingStone("neblaze", ModSpirits.neblaze, ModMultiBlocks.altar_neblaze)
        calling_stone_redwind = ItemCallingStone("redwind", ModSpirits.redwind, ModMultiBlocks.altar_redwind)
        calling_stone_romol = ItemCallingStone("romol", ModSpirits.romol, ModMultiBlocks.altar_romol)
        calling_stone_squarefury = ItemCallingStone("squarefury", ModSpirits.squarefury, ModMultiBlocks.altar_squarefury)
        calling_stone_timber = ItemCallingStone("timber", ModSpirits.timber, ModMultiBlocks.altar_timber)
    }
}