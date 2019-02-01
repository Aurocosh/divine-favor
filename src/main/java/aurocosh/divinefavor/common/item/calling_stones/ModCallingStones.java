package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModCallingStones {
    public static ItemCallingStone calling_stone_allfire;
    public static ItemCallingStone calling_stone_arbow;
    public static ItemCallingStone calling_stone_blizrabi;
    public static ItemCallingStone calling_stone_endererer;
    public static ItemCallingStone calling_stone_nefir;
    public static ItemCallingStone calling_stone_redwind;
    public static ItemCallingStone calling_stone_romol;
    public static ItemCallingStone calling_stone_squarefury;
    public static ItemCallingStone calling_stone_timber;

    public static void preInit() {
        calling_stone_allfire = new ItemCallingStone("allfire", ModSpirits.allfire, ModMultiBlocks.altar_allfire);
        calling_stone_arbow = new ItemCallingStone("arbow", ModSpirits.arbow, ModMultiBlocks.altar_arbow);
        calling_stone_blizrabi = new ItemCallingStone("blizrabi", ModSpirits.blizrabi, ModMultiBlocks.altar_blizrabi);
        calling_stone_endererer = new ItemCallingStone("endererer", ModSpirits.endererer, ModMultiBlocks.altar_endererer);
        calling_stone_nefir = new ItemCallingStone("nefir", ModSpirits.nefir, ModMultiBlocks.altar_nefir);
        calling_stone_redwind = new ItemCallingStone("redwind", ModSpirits.redwind, ModMultiBlocks.altar_redwind);
        calling_stone_romol = new ItemCallingStone("romol", ModSpirits.romol, ModMultiBlocks.altar_romol);
        calling_stone_squarefury = new ItemCallingStone("squarefury", ModSpirits.squarefury, ModMultiBlocks.altar_squarefury);
        calling_stone_timber = new ItemCallingStone("timber", ModSpirits.timber, ModMultiBlocks.altar_timber);
    }
}